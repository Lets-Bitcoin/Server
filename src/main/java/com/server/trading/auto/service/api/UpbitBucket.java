package com.server.trading.auto.service.api;


import java.util.LinkedList;
import java.util.List;

public class UpbitBucket {
    private final List<UpbitAbstract> apiList;

    public UpbitBucket() {
        this.apiList = new LinkedList<>();
    }

    public UpbitBucket add(UpbitAbstract upbitAbstract) {
        this.apiList.add(upbitAbstract);
        return this;
    }

    public void execute() {
        this.apiList.forEach(UpbitAbstract::call);
    }
}
