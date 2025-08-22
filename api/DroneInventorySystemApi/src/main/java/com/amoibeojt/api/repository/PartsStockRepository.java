package com.amoibeojt.api.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amoibeojt.api.dto.partsstock.PartsStockResponseDTO;
import com.amoibeojt.api.entity.PartsStock;

/**
 * 部品在庫テーブルリポジトリー
 *
 * @author	your name
 * 
 */
public interface PartsStockRepository extends JpaRepository<PartsStock, Integer> {
	@Query("""
			SELECT new com.amoibeojt.api.dto.partsstock.PartsStockResponseDTO(
					ps.stockId,
					c.categoryName,
					ce.centerName,
					ps.name,
					ps.amount,
					ps.description
					)
			FROM PartsStock ps
			JOIN PartsCategoryInfo c ON ps.categoryId = c.categoryId
			JOIN CenterInfo ce ON ps.centerId = ce.centerId
			WHERE (:centerIds IS NULL OR ps.centerId IN :centerIds)
			AND (:categoryIds IS NULL OR ps.categoryId IN :categoryIds)
			AND (:stockIds IS NULL OR ps.stockId IN :stockIds)
			AND (:namePattern IS NULL OR LOWER(ps.name) LIKE LOWER(CONCAT('%', :namePattern, '%')))
			AND (:amountMin IS NULL OR ps.amount >= :amountMin)
			AND (:amountMax IS NULL OR ps.amount <= :amountMax)
			ORDER BY ps.stockId ASC
			""")
			List<PartsStockResponseDTO> searchByCriteriaWithJoin(
					@Param("centerIds") List<Integer> centerIds,
					@Param("categoryIds") List<Integer> categoryIds,
					@Param("stockIds") List<Integer> stockIds,
					@Param("namePattern") String namePattern,
					@Param("amountMin") Integer amountMin,
					@Param("amountMax") Integer amountMax
					);

	@Query("SELECT p FROM PartsStock p WHERE p.stockId = :stockId")
	Optional<PartsStock> findLatestByStockId(@Param("stockId") Integer stockId);
	
	@Modifying
	@Query(value = """
	    INSERT INTO parts_stock 
	    (stock_id, center_id, category_id, name, amount, description, delete_flag, create_date, update_date) 
	    VALUES (:stockId, :centerId, :categoryId, :name, :amount, :description, :deleteFlag, :createDate, :updateDate)
	    ON DUPLICATE KEY UPDATE 
	        amount = amount + VALUES(amount),
	        update_date = VALUES(update_date),
	        stock_id = LAST_INSERT_ID(stock_id)
	    """, nativeQuery = true)
	void upsertPartsStock(
	    @Param("stockId") Integer stockId,
	    @Param("centerId") Integer centerId,
	    @Param("categoryId") Integer categoryId,
	    @Param("name") String name,
	    @Param("amount") Integer amount,
	    @Param("description") String description,
	    @Param("deleteFlag") Boolean deleteFlag,
	    @Param("createDate") LocalDateTime createDate,
	    @Param("updateDate") LocalDateTime updateDate
	);
	
	// IDを取得するメソッドを追加
	@Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
	Integer getLastInsertId();
}