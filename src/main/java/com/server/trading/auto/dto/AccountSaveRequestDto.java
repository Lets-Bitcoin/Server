package com.server.trading.auto.dto;

import lombok.Getter;

@Getter
public class AccountSaveRequestDto {
    private String currency;                // 화폐를 의미하는 영문 대문자 코드
    private Double balance;                 // 주문가능 금액/수량
    private Double locked;                  // 주문 중 묶여있는 금액/수량
    private Double avg_buy_price;           // 매수평균가
    private Boolean avg_buy_price_modified; // 매수평균가 수정 여부
    private String unit_currency;           // 평단가 기준 화폐
}
