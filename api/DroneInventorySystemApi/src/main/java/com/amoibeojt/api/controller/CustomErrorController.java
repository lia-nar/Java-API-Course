package com.amoibeojt.api.controller;

import java.time.ZonedDateTime;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amoibeojt.api.dto.ErrorResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<ErrorResponseDTO> handleError(HttpServletRequest request) {
        Object statusObj = request.getAttribute("javax.servlet.error.status_code");
        Object messageObj = request.getAttribute("javax.servlet.error.message");

        int statusCode = (statusObj != null) ? Integer.parseInt(statusObj.toString()) : 400;
        String message = (messageObj != null) ? messageObj.toString() : "不明なエラー";

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            "error",
            getShortMessage(statusCode),
            getErrorCode(statusCode),
            message,
            ZonedDateTime.now().toString()
        );

        return ResponseEntity.status(statusCode).body(errorResponse);
    }

    private String getErrorCode(int statusCode) {
        return switch (statusCode) {
            case 400 -> "INVALID_REQUEST";
            case 401 -> "UNAUTHORIZED";
            case 403 -> "FORBIDDEN";
            case 404 -> "RESOURCE_NOT_FOUND";
            case 409 -> "DUPLICATE_REQUEST";
            case 422 -> "INSUFFICIENT_STOCK";
            case 500 -> "INTERNAL_ERROR";
            default -> "UNKNOWN_ERROR";
        };
    }

    private String getShortMessage(int statusCode) {
        return switch (statusCode) {
            case 400 -> "リクエストが不正です";
            case 401 -> "認証エラーです";
            case 403 -> "権限がありません";
            case 404 -> "リソースが見つかりません";
            case 409 -> "重複リクエストです";
            case 422 -> "在庫が不足しています";
            case 500 -> "サーバ内部エラーが発生しました";
            default -> "エラーが発生しました";
        };
    }
}