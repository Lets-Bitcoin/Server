package com.server.trading.auto.service;

import com.server.trading.auto.config.KafkaProducerCluster;
import com.server.trading.auto.dto.ValueSendRequestDto;
import com.server.trading.auto.service.api.UpbitBucket;
import com.server.trading.auto.service.api.account.GetAccounts;
import com.server.trading.auto.service.api.candle.CurrentValue;
import com.server.trading.auto.service.api.candle.MinuteCandle;
import com.server.trading.auto.util.RSICalculator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ValueService {
    private final KafkaProducerCluster producer;

    private final CurrentValue currentValue;
    private final MinuteCandle minuteCandle;
    private final GetAccounts getAccounts;

    @PostConstruct
    public void setup() {
        UpbitBucket upbitBucket = new UpbitBucket();
        upbitBucket
                .add(currentValue)
                .add(minuteCandle)
                .add(getAccounts);
        upbitBucket.execute();
    }

    // send data to batch server
    public void send() {
        List<ValueSendRequestDto> data = currentValue.getSendData();
        Map<String, Double> calculatedRSI = new RSICalculator().getCalculatedRSI(minuteCandle.getCandles());
        for (ValueSendRequestDto dto : data) {
            dto.setRsi(calculatedRSI.get(dto.getMarket().replace("KRW-", "")));
            producer.sendMessage(dto);
        }
    }
}
