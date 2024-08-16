package com.server.trading.auto.service;

import com.server.trading.auto.domain.Order;
import com.server.trading.auto.domain.OrderRepository;
import com.server.trading.auto.dto.OrderCreateRequestDto;
import com.server.trading.auto.dto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponseDto save(OrderCreateRequestDto orderCreateRequestDto) {
        Order order = orderCreateRequestDto.toEntity();
        return new OrderResponseDto().toDto(orderRepository.save(order));
    }
}
