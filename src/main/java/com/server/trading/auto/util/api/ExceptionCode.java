package com.server.trading.auto.util.api;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    JSON_PARSE_ERROR(4000, "Json 파싱에 실패했습니다."),
    ACCOUNT_FAILURE(4001, "계좌 정보를 가져오는 작업에 실패했습니다."),
    CURRENT_VALUE_FAILURE(4002, "현재값 정보를 가져오는 작업에 실패했습니다."),
    MINUTE_CANDLE_FAILURE(4003, "분봉 캔들 정보를 가져오는 작업에 실패했습니다."),
    DELETE_ORDER_FAILURE(4004, "주문을 취소하는 작업에 실패했습니다."),
    GET_ORDER_FAILURE(4005, "주문 목록을 가져오는 작업에 실패했습니다."),
    POST_ORDER_FAILURE(4006, "주문에 실패했습니다."),
    ORDER_NOT_EXIST(4007, "해당 코인을 보유하고 있지 않습니다."),
    DATA_NOT_SUFFICIENT(4008, "캔들의 수가 MACD 계산하기에 충분하지 않습니다.");

    private int code;
    private String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
