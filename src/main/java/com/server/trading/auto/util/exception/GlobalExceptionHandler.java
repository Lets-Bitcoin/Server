package com.server.trading.auto.util.exception;

import com.server.trading.auto.util.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UpbitApiException.class)
    public ApiResponse upbitException(UpbitApiException e) {
        return ApiResponse.fail(e.getExceptionCode(), e.getMessage());
    }
}
