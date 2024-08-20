package com.server.trading.auto.dto;

import lombok.Getter;

@Getter
public class AccountSaveRequestDto {
    private String currency;
    private Double balance;
    private Double locked;
    private Double avg_buy_price;
    private Boolean avg_buy_price_modified;
    private String unit_currency;
}
