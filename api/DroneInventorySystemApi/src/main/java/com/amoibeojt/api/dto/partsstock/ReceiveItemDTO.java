package com.amoibeojt.api.dto.partsstock;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import com.amoibeojt.api.entity.CenterInfo;
import com.amoibeojt.api.entity.PartsCategoryInfo;
import com.amoibeojt.api.validator.ExistsInTable;

import lombok.Data;

@Data
public class ReceiveItemDTO {
    //在庫ID
    private Integer stock_id;
    
    //入荷先センター ID
    @NotNull(message = "センターIDは必須です")
    @ExistsInTable(entity = CenterInfo.class, field = "center_id", message = "指定されたセンターIDは存在しません")
    private Integer center_id;
    
    //カテゴリ ID
    @NotNull(message = "カテゴリIDは必須です")
    @ExistsInTable(entity = PartsCategoryInfo.class, field = "category_id", message = "指定されたカテゴリIDは存在しません")
    private Integer category_id;
    
    //部品名
    @NotNull(message = "部品名は必須です")
    private String parts_name;
    
    //入荷数量
    @NotNull(message = "入荷数量は必須です")
    @Positive(message = "入荷数量は正の数である必要があります")
    private Integer receive_amount;
    
    //部品説明
    private String description;
}