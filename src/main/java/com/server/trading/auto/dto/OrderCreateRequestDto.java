package com.server.trading.auto.dto;

import com.server.trading.auto.domain.Order;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderCreateRequestDto {
    private String uuid;
    private String side;
    private String ordType;
    private String price;
    private String state;
    private String market;
    private String createdAt;
    private String volume;
    private String remainingVolume;
    private String reservedFee;
    private String remainingFee;
    private String paidFee;
    private String locked;
    private String executedVolume;
    private Integer tradesCount;

    public Order toEntity() {
        return Order.builder()
                .uuid(uuid)
                .side(side)
                .ordType(ordType)
                .price(price)
                .state(state)
                .market(market)
                .createdAt(createdAt)
                .volume(volume)
                .remainingFee(remainingFee)
                .reservedFee(reservedFee)
                .remainingFee(remainingFee)
                .paidFee(paidFee)
                .locked(locked)
                .executedVolume(executedVolume)
                .tradesCount(tradesCount)
                .build();
    }
}
