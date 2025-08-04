package com.amoibeojt.api.dto.partsstock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部品在庫照会のDTO
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartsStockRequestDTO {
	
	// センターID
	private Integer centerId;
    // 分類ID
	private Integer categoryId;
	//在庫ID
	private Integer stockId;
	//部品名
	private String namePattern;
	//在庫数量の最小値
	private Integer amountMin;
	//在庫数量の最大値
	private Integer amountMax;

}
