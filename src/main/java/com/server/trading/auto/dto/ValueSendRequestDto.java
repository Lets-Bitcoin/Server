package com.server.trading.auto.dto;

import com.server.trading.auto.domain.Value;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValueSendRequestDto {
    private String market;
    private String trade_date;
    private String trade_time;
    private String trade_date_kst;
    private String trade_time_kst;
    private Long trade_timestamp;
    private Double opening_price;
    private Double high_price;
    private Double low_price;
    private Double trade_price;
    private Double prev_closing_price;
    private String change;
    private Double change_price;
    private Double change_rate;
    private Double signed_change_price;
    private Double signed_change_rate;
    private Double trade_volume;
    private Long timestamp;

    @Builder
    public ValueSendRequestDto(
            String market,
            String trade_date,
            String trade_time,
            String trade_date_kst,
            String trade_time_kst,
            Long trade_timestamp,
            Double opening_price,
            Double high_price,
            Double low_price,
            Double trade_price,
            Double prev_closing_price,
            String change,
            Double change_price,
            Double change_rate,
            Double signed_change_price,
            Double signed_change_rate,
            Double trade_volume,
            Long timestamp
    ) {
        this.market = market;
        this.trade_date = trade_date;
        this.trade_time=trade_time;
        this.trade_date_kst = trade_date_kst;
        this.trade_time_kst = trade_time_kst;
        this.trade_timestamp = trade_timestamp;
        this.opening_price = opening_price;
        this.high_price = high_price;
        this.low_price = low_price;
        this.trade_price = trade_price;
        this.prev_closing_price = prev_closing_price;
        this.change = change;
        this.change_price = change_price;
        this.change_rate = change_rate;
        this.signed_change_price = signed_change_price;
        this.signed_change_rate = signed_change_rate;
        this.trade_volume = trade_volume;
        this.timestamp = timestamp;
    }

    public Value toEntity() {
        return Value.builder()
                .market(market)
                .trade_date(trade_date)
                .trade_time(trade_time)
                .trade_date_kst(trade_date_kst)
                .trade_time_kst(trade_time_kst)
                .trade_timestamp(trade_timestamp)
                .opening_price(opening_price)
                .high_price(high_price)
                .low_price(low_price)
                .trade_price(trade_price)
                .prev_closing_price(prev_closing_price)
                .change(change)
                .change_price(change_price)
                .change_rate(change_rate)
                .signed_change_price(signed_change_price)
                .signed_change_rate(signed_change_rate)
                .trade_volume(trade_volume)
                .timestamp(timestamp)
                .build();
    }
}
