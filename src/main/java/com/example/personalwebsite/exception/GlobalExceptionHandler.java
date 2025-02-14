package com.example.personalwebsite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler 全局異常處理器
 *
 * 此類別透過 @RestControllerAdvice 標註，讓 Spring 在整個應用中自動捕捉 Controller 拋出的異常，
 * 並根據不同的異常類型返回適當的 HTTP 狀態碼和錯誤訊息。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 處理所有 RuntimeException 異常
     * 當 Controller 層或 Service 層拋出 RuntimeException 時，此方法會被觸發，並返回 HTTP 500 (Internal Server Error) 與異常訊息。
     *
     * @param runtimeException 捕捉到的 RuntimeException
     * @return 包含異常訊息的 ResponseEntity，HTTP 狀態為 500
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException runtimeException) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(runtimeException.getMessage());
    }
}
