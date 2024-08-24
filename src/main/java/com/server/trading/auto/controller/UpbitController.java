package com.server.trading.auto.controller;

import com.server.trading.auto.service.OrderService;
import com.server.trading.auto.service.ValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpbitController {
    private final OrderService orderService;
    private final ValueService valueService;

    @GetMapping("/")
    public String send() {
        valueService.send();
        return "ok";
    }
}
