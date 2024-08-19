package com.server.trading.auto.util.exception;

import com.server.trading.auto.util.api.ExceptionCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final ExceptionCode exceptionCode;
    private final String message;

    public BaseException(ExceptionCode exceptionCode, String message) {
        this.exceptionCode = exceptionCode;
        this.message = message;
    }
}
