package com.server.trading.auto.service;

import com.server.trading.auto.domain.Order;
import com.server.trading.auto.domain.OrderRepository;
import com.server.trading.auto.dto.AccountSaveRequestDto;
import com.server.trading.auto.dto.OrderCreateRequestDto;
import com.server.trading.auto.dto.OrderResponseDto;
import com.server.trading.auto.service.api.account.GetAccounts;
import com.server.trading.auto.service.api.candle.CurrentValue;
import com.server.trading.auto.service.api.candle.MinuteCandle;
import com.server.trading.auto.service.api.order.DeleteOrder;
import com.server.trading.auto.service.api.order.PostOrders;
import com.server.trading.auto.util.CoinListUtil;
import com.server.trading.auto.util.strategy.MACDCalculator;
import com.server.trading.auto.util.OrderType;
import com.server.trading.auto.util.strategy.RSICalculator;
import com.server.trading.auto.util.strategy.RatioCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final PostOrders postOrders;
    private final DeleteOrder deleteOrder;

    private final GetAccounts getAccounts;
    private final CurrentValue currentValue;
    private final MinuteCandle minuteCandle;

    public OrderResponseDto save(OrderCreateRequestDto orderCreateRequestDto) {
        Order order = orderCreateRequestDto.toEntity();
        return new OrderResponseDto().toDto(orderRepository.save(order));
    }

    @Transactional
    public void order() {
        Double asset = getAccounts.getAsset();              // total asset KRW in wallet
        Map<String, Double> coins = getAccounts.getCoins(); // each coin in wallet
        Double KRW = coins.get("KRW");                      // KRW in wallet
        List<AccountSaveRequestDto> account = getAccounts.getAccount();         // each details of coins in wallet
        Map<String, Integer> currentValues = currentValue.getCurrentValue();    // current values of coins
        Map<String, Double> RSI = new RSICalculator().calculate(minuteCandle.getCandles());                 // rsi
        Map<String, ArrayList<Integer>> MACD = new MACDCalculator().calculate(minuteCandle.getCandles());   // macd (50, 200)

        for (String symbol : CoinListUtil.getList()) {
            Optional<AccountSaveRequestDto> dto = account.stream()
                    .filter(coin -> coin.getCurrency().equals(symbol))
                    .findAny();

            if (dto.isEmpty()) continue;

            Double ratio = new RatioCalculator().calculate(currentValues.get(symbol), dto.get().getAvg_buy_price());
            Double coinRSI = RSI.get(symbol);
            ArrayList<Integer> coinMACD = MACD.get(symbol);

            if ((coinRSI > 70 && (coinMACD.get(0) > coinMACD.get(1)) || coinRSI > 83 || ratio > 0.5)
                    && ratio > 0.35
                    && coins.containsKey(symbol)) {
                // (rsi > 70, macd(50) > macd(200)) or (rsi > 83) or (ratio > 5%)
                // ratio > 3.5%
                // have coin
                orderRepository.save(postOrders.postOrder(symbol, OrderType.ASK, 100).toEntity(coinRSI, coinMACD.get(0), coinMACD.get(1)));
            } else if ((coinRSI < 25 && (coinMACD.get(0) < coinMACD.get(1)) || coinRSI < 15)
                    && KRW >= 5000) {
                // (rsi < 25, macd(50) < macd(200)) or (rsi < 15)
                // have money
                if (coins.containsKey(symbol)) {
                    if (coinRSI <= 9) {
                        orderRepository.save(postOrders.postOrder(symbol, OrderType.BID, 10).toEntity(coinRSI, coinMACD.get(0), coinMACD.get(1)));
                    }
                } else {
                    orderRepository.save(postOrders.postOrder(symbol, OrderType.BID, 10).toEntity(coinRSI, coinMACD.get(0), coinMACD.get(1)));
                }
            }
        }
    }
}
