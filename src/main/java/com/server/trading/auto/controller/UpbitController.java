package com.server.trading.auto.controller;

import com.server.trading.auto.dto.AccountSaveRequestDto;
import com.server.trading.auto.dto.OrderWaitResponseDto;
import com.server.trading.auto.service.api.UpbitBucket;
import com.server.trading.auto.service.api.account.GetAccounts;
import com.server.trading.auto.service.api.candle.CurrentValue;
import com.server.trading.auto.service.api.candle.MinuteCandle;
import com.server.trading.auto.service.api.order.DeleteOrder;
import com.server.trading.auto.service.api.order.GetOrders;
import com.server.trading.auto.service.api.order.PostOrders;
import com.server.trading.auto.util.OrderType;
import com.server.trading.auto.util.RSICalculator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UpbitController {
    private final CurrentValue currentValue;
    private final MinuteCandle minuteCandle;
    private final GetAccounts getAccounts;

    private final GetOrders getOrders;
    private final PostOrders postOrders;
    private final DeleteOrder deleteOrder;

    @PostConstruct
    public void setup() {
        UpbitBucket upbitBucket = new UpbitBucket();
        upbitBucket
                .add(currentValue)
                .add(minuteCandle)
                .add(getAccounts);
        upbitBucket.execute();
    }

    // get account : asset
    @GetMapping("/asset")
    public Double getAsset() {
        return getAccounts.getCapital();
    }

    // get account : each coin
    @GetMapping("/account")
    public List<AccountSaveRequestDto> getAccount() {
        return getAccounts.getAccount();
    }

    // get current values
    @GetMapping("/current/value")
    public String getCurrentValue() {
        System.out.println(currentValue.getCurrentValue().toString());
        return null;
    }

    // get minute candles
    @GetMapping("/minute/candle")
    public String getMinuteCandle() {
        return minuteCandle.getCandles().toString();
    }

    // calculate rsi
    @GetMapping("/rsi")
    public String getRSI() {
        return new RSICalculator().getCalculatedRSI(minuteCandle.getCandles()).toString();
    }

    // get order list
    @GetMapping("/order")
    public List<OrderWaitResponseDto> getOrders() {
        getOrders.getOrder();
        return getOrders.getOrders();
    }

    // delete order
    @DeleteMapping("/order")
    public void deleteOrders() {
        deleteOrder.deleteOrder();
    }

    // post order
    @PostMapping("/order")
    public void postOrders() {
        postOrders.postOrder("BTC", OrderType.ASK, 2);
    }

}
