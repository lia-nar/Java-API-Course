package com.amoibeojt.api.exception;

import java.time.ZonedDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.amoibeojt.api.dto.ErrorResponseDTO;

/**
 * グローバル例外ハンドラ
 * 
 * @author your name
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorized(UnauthorizedException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            "error",
            "認証に失敗しました",
            "UNAUTHORIZED",
            ex.getMessage(),
            ZonedDateTime.now().toString()
        );
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponseDTO> handleForbidden(ForbiddenException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            "error",
        	"権限がありません",
            "FORBIDDEN",
            ex.getMessage(),
            ZonedDateTime.now().toString()
        );
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            "error",
            "リソースが見つかりません",
            "RESOURCE_NOT_FOUND",
            ex.getMessage(),
            ZonedDateTime.now().toString()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
	
    @ExceptionHandler(DuplicateRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflict(DuplicateRequestException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            "error",
            "重複リクエストです",
            "DUPLICATE_REQUEST",
            ex.getMessage(),
            ZonedDateTime.now().toString()
        );
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
	
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponseDTO> handleInsufficientStock(InsufficientStockException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            "error",
            "在庫が不足しています",
            "INSUFFICIENT_STOCK",
            ex.getMessage(),
            ZonedDateTime.now().toString()
        );
        
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler({InvalidInputException.class, MethodArgumentTypeMismatchException.class,
    	ConversionFailedException.class,MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorResponseDTO> handleInvalidInput(InvalidInputException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            "error",
            "入力が無効です",
            "INVALID_REQUEST",
            ex.getMessage(),
            ZonedDateTime.now().toString()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAllExceptions(Exception ex) {
    	String userMessage = null;
        String errorMessage = ex.getMessage();
        if (errorMessage != null && errorMessage.contains("default message")) {
        	userMessage = extractUserFriendlyMessage(errorMessage);
        }
    	
        ErrorResponseDTO error = new ErrorResponseDTO(
            "error",
            "予期しないエラーが発生しました",
            "INTERNAL_ERROR",
            userMessage,
            ZonedDateTime.now().toString()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    
    // 全てのdefault messageを抽出するヘルパーメソッド
    private String extractUserFriendlyMessage(String errorMessage) {
        Pattern pattern = Pattern.compile("default message \\[([^\\]]+)\\]");
        Matcher matcher = pattern.matcher(errorMessage);
        
        String japaneseMessage = "予期しないエラーが発生しました";
        while (matcher.find()) {
            String message = matcher.group(1);
            japaneseMessage = message;
        }
        return japaneseMessage;
    }
    
}