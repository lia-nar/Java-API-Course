package com.amoibeojt.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * APIの共通レスポンスのページング返却用ラッパー
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {
	// 実データのリスト
    private List<T> items;
    // 合計件数
    private long total_count;

}
