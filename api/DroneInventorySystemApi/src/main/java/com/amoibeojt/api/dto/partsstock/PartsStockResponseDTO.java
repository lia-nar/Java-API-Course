package com.amoibeojt.api.dto.partsstock;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * レスポンス用 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartsStockResponseDTO {
    //stock_id
    private Integer stockId;
    //カテゴリ名
    private String categoryName;
    //センター名
    private String centerName;
    //部品名
    private String name;
    //在庫数
    private Integer amount;
    //説明
    private String description;

}
