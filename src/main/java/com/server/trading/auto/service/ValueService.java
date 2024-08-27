package com.server.trading.auto.service;

import com.server.trading.auto.config.KafkaProducerCluster;
import com.server.trading.auto.dto.MinuteCandleValueResponseDto;
import com.server.trading.auto.dto.ValueSendRequestDto;
import com.server.trading.auto.service.api.candle.CurrentValue;
import com.server.trading.auto.service.api.candle.MinuteCandle;
import com.server.trading.auto.util.strategy.MACDCalculator;
import com.server.trading.auto.util.strategy.RSICalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ValueService {
    private final KafkaProducerCluster producer;

    private final CurrentValue currentValue;
    private final MinuteCandle minuteCandle;

    // send data to batch server
    public void send() {
        List<ValueSendRequestDto> data = currentValue.getSendData();
        Map<String, List<MinuteCandleValueResponseDto>> candles = minuteCandle.getCandles();
        Map<String, Double> calculatedRSI = new RSICalculator().calculate(candles);
        Map<String, ArrayList<Integer>> calculatedMACD = new MACDCalculator().calculate(candles);
        System.out.println(calculatedMACD);
        System.out.println(new MACDCalculator().calculate(minuteCandle.getCandles()));
        for (ValueSendRequestDto dto : data) {
            dto.setRsi(calculatedRSI.get(dto.getMarket().replace("KRW-", "")));
            dto.setMacd_50(calculatedMACD.get(dto.getMarket().replace("KRW-", "")).get(0));
            dto.setMacd_200(calculatedMACD.get(dto.getMarket().replace("KRW-", "")).get(1));
            producer.sendMessage(dto);
        }
    }
}
