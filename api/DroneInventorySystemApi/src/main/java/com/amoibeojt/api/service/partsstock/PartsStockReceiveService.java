package com.amoibeojt.api.service.partsstock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amoibeojt.api.dto.partsstock.ReceiveItemDTO;
import com.amoibeojt.api.dto.partsstock.ReceiveRequestDTO;
import com.amoibeojt.api.entity.PartsStock;
import com.amoibeojt.api.entity.PartsStockHistory;
import com.amoibeojt.api.exception.InvalidInputException;
import com.amoibeojt.api.repository.PartsStockHistoryRepository;
import com.amoibeojt.api.repository.PartsStockRepository;

import lombok.RequiredArgsConstructor;

/**
 * 部品入荷のサービスクラス
 * 
 * @author your name
 */
@Service
@RequiredArgsConstructor
public class PartsStockReceiveService {
	
    private final PartsStockRepository repository;
    
    private final PartsStockHistoryRepository historyRepository;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * 既存在庫数量を取得
     * @param stockId 在庫ID
     * @return amount（存在しない場合は 0）
     */
    @Transactional(readOnly = true)
    public Integer findExistingStockAmount(Integer stockId) {
        return repository.findLatestByStockId(stockId)
                .map(PartsStock::getAmount)
                .orElse(0);
    }
    
    /**
     * 部品在庫を更新または新規登録
     * @param item
     * @return stock_id
     */
    @Transactional
	public Integer saveOrUpdatePartsStock(ReceiveItemDTO item) {
        
        try {
        	LocalDateTime now = LocalDateTime.now();
        	
            // レコード登録・更新
            repository.upsertPartsStock(
                    item.getStock_id(),
                    item.getCenter_id(),
                    item.getCategory_id(),
                    item.getParts_name(),          // DTOのparts_name → テーブルのname
                    item.getReceive_amount(),      // DTOのreceive_amount → テーブルのamount
                    item.getDescription(),
                    false,                         // delete_flag
                    now,                          // create_date
                    now                           // update_date
                );
            // 新規・更新どちらでもIDを取得
            return repository.getLastInsertId();
            
        } catch (DataAccessException e) {
            throw new InvalidInputException("部品在庫登録処理中にDBエラーが発生しました。");
        } catch (Exception e) {
            throw new InvalidInputException("部品在庫の登録に失敗しました: StockId=" + item.getStock_id());
        }
    }
	
    /**
     * 部品在庫履歴を登録
     * @param actualStockId
     * @param beforeAmount
     * @param item
     * @param request
     * @return 登録された履歴情報
     */
    @Transactional
    public PartsStockHistory insertPartsStockHistory(Integer actualStockId, Integer beforeAmount, ReceiveItemDTO item, ReceiveRequestDTO request) {

        LocalDateTime transactionDate = parseTransactionDate(request.getTransaction_date());
        PartsStockHistory newHistory = PartsStockHistoryRepository.build(actualStockId, beforeAmount, item, request, transactionDate);

        try {
        	return historyRepository.upsertHistory(newHistory);
        } catch (DataAccessException e) {
            throw new InvalidInputException("部品在庫履歴登録処理中にDBエラーが発生しました。");
        } catch (Exception e) {
            throw new InvalidInputException("部品在庫履歴の登録に失敗しました: StockId=" + actualStockId);
        }
    }
    
    /**
     * 取引日付の解析
     * @param transactionDateStr 取引日付文字列
     * @return LocalDateTime
     */
    private LocalDateTime parseTransactionDate(String transactionDateStr) {
        if (transactionDateStr == null || transactionDateStr.isBlank()) {
            throw new InvalidInputException("取引日付が指定されていません。");
        }
        try {
            LocalDate date = LocalDate.parse(transactionDateStr, DATE_FORMATTER);
            return date.atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("取引日付の形式が不正です: " + transactionDateStr);
        }
    }

}
