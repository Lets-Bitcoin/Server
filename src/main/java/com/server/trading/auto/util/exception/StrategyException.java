package com.server.trading.auto.util.exception;

import com.server.trading.auto.util.api.ExceptionCode;

public class StrategyException extends BaseException {
    public StrategyException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }
}
