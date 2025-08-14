package com.amoibeojt.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amoibeojt.api.entity.PartsStockHistory;

/**
 * 部品入出庫履歴テーブルリポジトリー
 *
 * @author	your name
 * 
 */
public interface PartsStockHistoryRepository  extends JpaRepository<PartsStockHistory, Integer>{
	
	@Query("SELECT p FROM PartsStockHistory p WHERE p.stockId = :stockId ORDER BY p.updateDate DESC LIMIT 1")
	Optional<PartsStockHistory> findFirstByStockIdOrderByCreateDateDesc(@Param("stockId") Integer stockId);
	
}
