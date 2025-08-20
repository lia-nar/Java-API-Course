package com.amoibeojt.api.service.partsstock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

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
     * @return 実際のstock_id
     */
    @Transactional
	public Integer saveOrUpdatePartsStock(ReceiveItemDTO item) {
        
        try {
        	
            // 部品在庫テーブルから既存レコードを検索
            Optional<PartsStock> stockOpt = repository.findLatestByStockId(item.getStock_id());
            Integer actualStockId;
            
            if (stockOpt.isPresent()) {
                // 既存レコード更新
                PartsStock stock = stockOpt.get();
                stock.setAmount(stock.getAmount() + item.getReceive_amount());
                stock.setUpdateDate(LocalDateTime.now());
                repository.save(stock);
                actualStockId = stock.getStockId();
                
            } else {
                // 新規レコード登録
                PartsStock newStock = new PartsStock();
                newStock.setCenterId(item.getCenter_id());
                newStock.setCategoryId(item.getCategory_id());
                newStock.setName(item.getParts_name());
                newStock.setAmount(item.getReceive_amount());
                newStock.setDescription(item.getDescription());
                newStock.setDeleteFlag(false);
                newStock.setCreateDate(LocalDateTime.now());
                newStock.setUpdateDate(LocalDateTime.now());
                
                PartsStock savedStock = repository.save(newStock);
                actualStockId = savedStock.getStockId();
            }
            
            return actualStockId;
            
        } catch (DataAccessException e) {
            throw new InvalidInputException("部品在庫登録処理中にDBエラーが発生しました。");
        } catch (Exception e) {
            throw new InvalidInputException("部品在庫の登録に失敗しました: StockId=" + item.getStock_id());
        }
    }
	
    /**
     * 部品在庫履歴を登録
     * @param actualStockId 実際のstock_id
     * @param beforeAmount 既存のamount（存在しない場合は 0）
     * @param item 入荷アイテム情報
     * @param request 入荷リクエスト情報
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
