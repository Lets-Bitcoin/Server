package com.server.trading.auto.dto;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MinuteCandleValueResponseDto {
    private String market;
    private String candle_date_time_utc;
    private String candle_date_time_kst;
    private Double opening_price;
    private Double high_price;
    private Double low_price;
    private Double trade_price;
    private Long timestamp;
    private Double candle_acc_trade_price;
    private Double candle_acc_trade_volume;
    private Integer unit;

    public MinuteCandleValueResponseDto(JsonObject object) {
        this.market = object.get("market").getAsString();
        this.candle_date_time_utc = object.get("candle_date_time_utc").getAsString();
        this.candle_date_time_kst = object.get("candle_date_time_kst").getAsString();
        this.opening_price = object.get("opening_price").getAsDouble();
        this.high_price = object.get("high_price").getAsDouble();
        this.low_price = object.get("low_price").getAsDouble();
        this.trade_price = object.get("trade_price").getAsDouble();
        this.timestamp = object.get("timestamp").getAsLong();
        this.candle_acc_trade_price = object.get("candle_acc_trade_price").getAsDouble();
        this.candle_acc_trade_volume = object.get("candle_acc_trade_volume").getAsDouble();
        this.unit = object.get("unit").getAsInt();
    }
}
