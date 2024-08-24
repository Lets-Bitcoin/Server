package com.server.trading.auto.dto;

import com.google.gson.JsonObject;
import com.server.trading.auto.domain.Value;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Integer high_price;
    private Integer low_price;
    private Integer trade_price;
    private Double prev_closing_price;
    private String change;
    private Double change_price;
    private Double change_rate;
    private Double signed_change_price;
    private Double signed_change_rate;
    private Double trade_volume;
    private Double acc_trade_price;
    private Double acc_trade_price_24h;
    private Double acc_trade_volume;
    private Double acc_trade_volume_24h;
    private Double highest_52_week_price;
    private String highest_52_week_date;
    private Double lowest_52_week_price;
    private String lowest_52_week_date;
    private Long timestamp;
    @Setter
    private Double rsi;

    @Builder
    public ValueSendRequestDto(
            String market,
            String trade_date,
            String trade_time,
            String trade_date_kst,
            String trade_time_kst,
            Long trade_timestamp,
            Double opening_price,
            Integer high_price,
            Integer low_price,
            Integer trade_price,
            Double prev_closing_price,
            String change,
            Double change_price,
            Double change_rate,
            Double signed_change_price,
            Double signed_change_rate,
            Double trade_volume,
            Double acc_trade_price,
            Double acc_trade_price_24h,
            Double acc_trade_volume,
            Double acc_trade_volume_24h,
            Double highest_52_week_price,
            String highest_52_week_date,
            Double lowest_52_week_price,
            String lowest_52_week_date,
            Long timestamp
    ) {
        this.market = market;
        this.trade_date = trade_date;
        this.trade_time = trade_time;
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
        this.acc_trade_price = acc_trade_price;
        this.acc_trade_price_24h = acc_trade_price_24h;
        this.acc_trade_volume = acc_trade_volume;
        this.acc_trade_volume_24h = acc_trade_volume_24h;
        this.highest_52_week_price = highest_52_week_price;
        this.highest_52_week_date = highest_52_week_date;
        this.lowest_52_week_price = lowest_52_week_price;
        this.lowest_52_week_date = lowest_52_week_date;
        this.timestamp = timestamp;
    }

    public ValueSendRequestDto(JsonObject object) {
        this.market = object.get("market").getAsString();
        this.trade_date = object.get("trade_date").getAsString();
        this.trade_time = object.get("trade_time").getAsString();
        this.trade_date_kst = object.get("trade_date_kst").getAsString();
        this.trade_time_kst = object.get("trade_time_kst").getAsString();
        this.trade_timestamp = object.get("trade_timestamp").getAsLong();
        this.opening_price = object.get("opening_price").getAsDouble();
        this.high_price = object.get("high_price").getAsInt();
        this.low_price = object.get("low_price").getAsInt();
        this.trade_price = object.get("trade_price").getAsInt();
        this.prev_closing_price = object.get("prev_closing_price").getAsDouble();
        this.change = object.get("change").getAsString();
        this.change_price = object.get("change_price").getAsDouble();
        this.change_rate = object.get("change_rate").getAsDouble();
        this.signed_change_price = object.get("signed_change_price").getAsDouble();
        this.signed_change_rate = object.get("signed_change_rate").getAsDouble();
        this.trade_volume = object.get("trade_volume").getAsDouble();
        this.acc_trade_price = object.get("acc_trade_price").getAsDouble();
        this.acc_trade_price_24h = object.get("acc_trade_price_24h").getAsDouble();
        this.acc_trade_volume = object.get("acc_trade_volume").getAsDouble();
        this.acc_trade_volume_24h = object.get("acc_trade_volume_24h").getAsDouble();
        this.highest_52_week_price = object.get("highest_52_week_price").getAsDouble();
        this.highest_52_week_date = object.get("highest_52_week_date").getAsString();
        this.lowest_52_week_price = object.get("lowest_52_week_price").getAsDouble();
        this.lowest_52_week_date = object.get("lowest_52_week_date").getAsString();
        this.timestamp = object.get("timestamp").getAsLong();
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
                .acc_trade_price(acc_trade_price)
                .acc_trade_price_24h(acc_trade_price_24h)
                .acc_trade_volume(acc_trade_volume)
                .acc_trade_volume_24h(acc_trade_volume_24h)
                .highest_52_week_date(highest_52_week_date)
                .highest_52_week_price(highest_52_week_price)
                .lowest_52_week_date(lowest_52_week_date)
                .lowest_52_week_price(lowest_52_week_price)
                .timestamp(timestamp)
                .build();
    }
}
