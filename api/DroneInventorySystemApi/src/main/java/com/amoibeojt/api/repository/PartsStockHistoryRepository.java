package com.amoibeojt.api.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amoibeojt.api.dto.partsstock.ReceiveItemDTO;
import com.amoibeojt.api.dto.partsstock.ReceiveRequestDTO;
import com.amoibeojt.api.entity.PartsStockHistory;

/**
 * 部品入出庫履歴テーブルリポジトリー
 *
 * @author	your name
 * 
 */
@Repository
public interface PartsStockHistoryRepository extends JpaRepository<PartsStockHistory, Integer> {
	
    /**
     * 履歴レコードの登録または更新
     * @param history 履歴エンティティ
     * @return 保存された履歴エンティティ
     */
    default PartsStockHistory upsertHistory(PartsStockHistory history) {
        return save(history);
    }
	
    /**
     * 履歴エンティティを構築するためのファクトリメソッド
     * @param actualStockId 実際のstock_id
     * @param beforeAmount 更新前の在庫数量
     * @param item 入荷アイテム情報
     * @param request 入荷リクエスト情報
     * @param transactionDate 取引日時
     * @return 構築された履歴エンティティ
     */
    public static PartsStockHistory build(
            Integer actualStockId,
            Integer beforeAmount,
            ReceiveItemDTO item,
            ReceiveRequestDTO request,
            LocalDateTime transactionDate) {

    	return PartsStockHistory.builder()
    	.stockId(actualStockId)
        .transactionType(request.getTransaction_type())
        .transactionDate(transactionDate)
        .amountBefore(beforeAmount)
        .amountChange(item.getReceive_amount())
        .amountAfter(beforeAmount + item.getReceive_amount())
        .supplierName(request.getSupplier_name())
        .purchaseOrderNo(request.getPurchase_order_no())
        .operatorName(request.getOperator_name())
        .deleteFlag(false)
        .createDate(LocalDateTime.now())
        .updateDate(LocalDateTime.now())
        .build();
    }
	
}
