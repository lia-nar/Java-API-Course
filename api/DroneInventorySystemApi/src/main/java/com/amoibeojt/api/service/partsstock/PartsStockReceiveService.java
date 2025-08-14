package com.amoibeojt.api.service.partsstock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amoibeojt.api.dto.ApiResponse;
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
    
	 /**
     * 部品入出庫履歴テーブルの更新/登録 
     * @param request 部品入荷DTO
     * @return ApiResponse
     */
	@Transactional
    public ApiResponse<String> receiveStock(ReceiveRequestDTO request) {
    	
    	List<ReceiveItemDTO> items = request.getItems();
    	
    	try {
        	// 個別のアイテムをList<ReceiveItemDTO> はリスト（配列）から取得
            for (ReceiveItemDTO item : items) {
                Integer stockId = item.getStock_id();
                Integer centerId = item.getCenter_id();
                Integer category_id  = item.getCategory_id();
                String partsName = item.getParts_name();
                Integer receive_amount = item.getReceive_amount();
                String description  = item.getDescription();
                
                // 実際に使用するstock_id
                Integer actualStockId;
                
                //  部品在庫テーブルから既存レコードを検索
                Optional<PartsStock> stockOpt = repository.findLatestByStockId(stockId);
                PartsStock stock = stockOpt.orElse(null);
                
                // 部品在庫 既存レコード更新
                if (stockOpt.isPresent()) {
                    stock.setAmount(stock.getAmount() + receive_amount);
                    stock.setUpdateDate(LocalDateTime.now());
                    repository.save(stock);
                    actualStockId = stock.getStockId();
                } else {
                    // 部品在庫 新規レコード登録
                    PartsStock newStock = new PartsStock();
                    newStock.setCenterId(centerId);
                    newStock.setCategoryId(category_id);
                    newStock.setName(partsName);
                    newStock.setAmount(receive_amount);
                    newStock.setDescription(description);
                    newStock.setDeleteFlag(false);
                    newStock.setCreateDate(LocalDateTime.now());
                    newStock.setUpdateDate(LocalDateTime.now());
                    PartsStock savedStock = repository.save(newStock);
                    actualStockId = savedStock.getStockId();
                }
                
                // 履歴テーブルから既存の最新レコードを取得
                Optional<PartsStockHistory> historyOpt = historyRepository.findFirstByStockIdOrderByCreateDateDesc(actualStockId);
                PartsStockHistory history = historyOpt.orElse(null);
                
                // 部品在庫履歴 新規レコード登録
                PartsStockHistory newHistory = new PartsStockHistory();
                newHistory.setStockId(actualStockId);
                newHistory.setTransactionType(request.getTransaction_type());
            	LocalDate date = LocalDate.parse(request.getTransaction_date());
            	LocalDateTime transactionDate = date.atStartOfDay();
                newHistory.setTransactionDate(transactionDate);
                newHistory.setAmountChange(receive_amount);
                if (historyOpt.isPresent()) {
                    newHistory.setAmountBefore(history.getAmountAfter());
                    newHistory.setAmountAfter(history.getAmountAfter() + receive_amount);
                } else {
                    newHistory.setAmountBefore(0);
                    newHistory.setAmountAfter(receive_amount);
                }
                newHistory.setSupplierName(request.getSupplier_name());
                newHistory.setPurchaseOrderNo(request.getPurchase_order_no());
                newHistory.setOperatorName(request.getOperator_name());
                newHistory.setDeleteFlag(false);
                newHistory.setCreateDate(LocalDateTime.now());
                newHistory.setUpdateDate(LocalDateTime.now());
                historyRepository.save(newHistory);

            }
    	} catch (Exception e) {
    	    throw new InvalidInputException("部品在庫履歴の登録に失敗しました");
    	}
    	return new ApiResponse<>("success", "部品入荷情報を正常に登録しました", null);
    }

}
