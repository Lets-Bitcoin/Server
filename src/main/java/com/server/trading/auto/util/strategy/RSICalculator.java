package com.server.trading.auto.util.strategy;

import com.server.trading.auto.dto.MinuteCandleValueResponseDto;

import java.util.*;

public class RSICalculator {

    public Map<String, Double> calculate(Map<String, List<MinuteCandleValueResponseDto>> values) {
        Map<String, Double> rsi = new HashMap<>();

        for (String coin : values.keySet()) {
            List<MinuteCandleValueResponseDto> prices = new ArrayList<>(values.get(coin));
            prices.sort(Comparator.comparing(MinuteCandleValueResponseDto::getCandle_date_time_kst));

            double zero = 0;
            List<Double> upList = new ArrayList<>();
            List<Double> downList = new ArrayList<>();
            for (int i = 0; i < prices.size() - 1; i++) {
                double delta = prices.get(i + 1).getTrade_price() - prices.get(i).getTrade_price();

                if (delta > 0) {
                    upList.add(delta);
                    downList.add(zero);
                } else if (delta < 0) {
                    upList.add(zero);
                    downList.add(delta * (-1));
                } else {
                    upList.add(zero);
                    downList.add(zero);
                }
            }

            double Day = 14;
            double a = (double) 1 / (1 + (Day - 1));

            double upEMA = 0;
            if (!upList.isEmpty()) {
                upEMA = upList.get(0);
                if (upList.size() > 1) {
                    for (int i = 1; i < upList.size(); i++) {
                        upEMA = (upList.get(i) * a) + (upEMA * (1 - a));
                    }
                }
            }

            double downEMA = 0;
            if (!downList.isEmpty()) {
                downEMA = downList.get(0);
                if (downList.size() > 1) {
                    for (int i = 1; i < downList.size(); i++) {
                        downEMA = (downList.get(i) * a) + (downEMA * (1 - a));
                    }
                }
            }

            double AU = upEMA;
            double AD = downEMA;
            double RS = AU / AD;
            double RSI = 100 - (100 / (1 + RS));
            rsi.put(coin, RSI);
        }

        return rsi;
    }
}