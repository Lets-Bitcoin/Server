package com.server.trading.auto.util.exception;

import com.server.trading.auto.util.api.ExceptionCode;

public class UpbitApiException extends BaseException {
    public UpbitApiException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }
}
