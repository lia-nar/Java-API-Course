package com.amoibeojt.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * APIの共通レスポンス
 *
 * @author yure name
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
	
	// "success" or "error"
    private String status;
    // 処理結果メッセージ
    private String message;
    // 実際のペイロード
    private T data;

}
