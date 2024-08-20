package com.server.trading.auto.util.api;

import lombok.Getter;

@Getter
public class ApiResponse {

    private final Integer code;
    private final String message;
    private final String error;

    public ApiResponse(Integer code, String message, String error) {
        this.code = code;
        this.message = message;
        this.error = error;
    }

    public static ApiResponse fail(ExceptionCode code, String error) {
        return new ApiResponse(code.getCode(), code.getMessage(), error);
    }
}
