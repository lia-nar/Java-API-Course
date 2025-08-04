package com.amoibeojt.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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

}