package com.server.trading.auto.dto;

import lombok.Getter;

@Getter
public class OrderWaitResponseDto {
    private String uuid;
    private String side;
    private String ord_type;
    private String price;
    private String state;
    private String market;
    private String created_at;
    private String volume;
    private String remaining_volume;
    private String reserved_fee;
    private String remaining_fee;
    private String paid_fee;
    private String locked;
    private String executed_volume;
    private String executed_funds;
    private Integer trades_count;
    private String time_in_force;
}
