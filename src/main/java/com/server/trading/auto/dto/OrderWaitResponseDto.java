package com.server.trading.auto.dto;

import com.server.trading.auto.domain.Order;
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
    private Double rsi;
    private Integer macd_50;
    private Integer macd_200;

    public Order toEntity(Double rsi, Integer macd_50, Integer macd_200) {
        return Order.builder()
                .uuid(uuid)
                .side(side)
                .ordType(ord_type)
                .price(price)
                .state(state)
                .market(market)
                .createdAt(created_at)
                .volume(volume)
                .remainingVolume(remaining_volume)
                .reservedFee(reserved_fee)
                .remainingFee(remaining_fee)
                .paidFee(paid_fee)
                .locked(locked)
                .executedVolume(executed_volume)
                .tradesCount(trades_count)
                .rsi(rsi)
                .macd_50(macd_50)
                .macd_200(macd_200)
                .build();
    }
}
