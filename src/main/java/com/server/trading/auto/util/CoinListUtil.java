package com.server.trading.auto.util;

import lombok.Getter;

import java.util.List;

@Getter
public class CoinListUtil {
    private static final List<String> coins = List.of("BTC", "ETH", "SOL");

    public static List<String> getList() {
        return coins;
    }
}
