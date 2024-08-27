package com.server.trading.auto.util.strategy;

public class RatioCalculator {

    public Double calculate(Integer currentPrice, Double avgBuyPrice) {
        double v = currentPrice - avgBuyPrice;
        return v / avgBuyPrice * 100;
    }
}
