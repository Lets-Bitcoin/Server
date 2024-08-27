package com.server.trading.auto.util.strategy;

import com.server.trading.auto.dto.MinuteCandleValueResponseDto;
import com.server.trading.auto.util.api.ExceptionCode;
import com.server.trading.auto.util.exception.StrategyException;

import java.util.*;

public class MACDCalculator {

    public Map<String, ArrayList<Integer>> calculate(Map<String, List<MinuteCandleValueResponseDto>> values) {
        Map<String, ArrayList<Integer>> result = new HashMap<>();
        List<Integer> periods = List.of(50, 200);
        ArrayList<Integer> macd;
        for (String symbol : values.keySet()) {
            macd = new ArrayList<>();
            List<MinuteCandleValueResponseDto> prices = new ArrayList<>(values.get(symbol));
            prices.sort(Comparator.comparing(MinuteCandleValueResponseDto::getCandle_date_time_kst));

            for (Integer period : periods) {
                if (prices.size() < period) throw new StrategyException(ExceptionCode.DATA_NOT_SUFFICIENT, null);
                long cal = (long) (prices.subList(prices.size() - period, prices.size()).stream()
                        .mapToDouble(MinuteCandleValueResponseDto::getTrade_price)
                        .sum() / period);
                macd.add((int) cal);
            }
            result.put(symbol, macd);
        }

        return result;
    }

}
