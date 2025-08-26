package com.amoibeojt.api.dto.partsstock;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

/**
 * 部品入荷のDTO
 *
 */
@Data
@Builder
public class ReceiveRequestDTO {
	
	//取引種別
	@NotNull(message = "取引種別は必須です")
	private String transaction_type;
	
	//入荷日時
	@NotNull(message = "入荷日時は必須です")
	private String transaction_date;
	
	//仕入先名
	@NotNull(message = "仕入先名は必須です")
	private String supplier_name;
	
	//発注書番号
	@NotNull(message = "発注書番号は必須です")
	private String purchase_order_no;
	
	//入荷作業者名
	@NotNull(message = "入荷作業者名は必須です")
	private String operator_name;
	
    //入荷部品リスト
	@Valid
	@NotNull(message = "入荷部品リストは必須です")
    private List<ReceiveItemDTO> items;

}
