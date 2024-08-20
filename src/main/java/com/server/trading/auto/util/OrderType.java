package com.server.trading.auto.util;

import lombok.Getter;

@Getter
public enum OrderType {

    BID("bid"),
    ASK("ask");

    private final String order;

    OrderType(String order) {
        this.order = order;
    }
}
