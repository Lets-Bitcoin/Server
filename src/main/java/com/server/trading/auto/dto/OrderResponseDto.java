package com.server.trading.auto.dto;

import com.server.trading.auto.domain.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private String uuid;
    private String side;
    private String ordType;
    private String price;
    private String state;
    private String market;
    private String createdAt;
    private String volume;
    private String remainingVolume;
    private String paidFee;
    private Integer tradesCount;

    @Builder
    public OrderResponseDto(
            String uuid,
            String side,
            String ordType,
            String price,
            String state,
            String market,
            String createdAt,
            String volume,
            String remainingVolume,
            String paidFee,
            Integer tradesCount
    ) {
        this.uuid = uuid;
        this.side = side;
        this.ordType = ordType;
        this.price = price;
        this.state = state;
        this.market = market;
        this.createdAt = createdAt;
        this.volume = volume;
        this.remainingVolume = remainingVolume;
        this.paidFee = paidFee;
        this.tradesCount = tradesCount;
    }

    public OrderResponseDto toDto(Order entity) {
        return OrderResponseDto.builder()
                .uuid(entity.getUuid())
                .side(entity.getSide())
                .ordType(entity.getOrdType())
                .price(entity.getPrice())
                .state(entity.getState())
                .market(entity.getMarket())
                .createdAt(entity.getCreatedAt())
                .volume(entity.getVolume())
                .paidFee(entity.getPaidFee())
                .tradesCount(entity.getTradesCount())
                .build();
    }
}
