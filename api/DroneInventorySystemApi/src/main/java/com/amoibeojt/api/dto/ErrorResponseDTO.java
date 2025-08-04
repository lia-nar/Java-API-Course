package com.amoibeojt.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * エラーメッセージをJSON形式で返却する
 *
 * @author yure name
 */
@Data
@AllArgsConstructor
public class ErrorResponseDTO {
    private String status;       // 常に "error"
    private String message;      // 概要
    private String error_code;   // 独自のエラーコード
    private String details;      // 例外詳細など
    private String timestamp;    // ISO日時
}