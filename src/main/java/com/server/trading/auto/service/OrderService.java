package com.server.trading.auto.service;

import com.server.trading.auto.domain.Order;
import com.server.trading.auto.domain.OrderRepository;
import com.server.trading.auto.dto.OrderCreateRequestDto;
import com.server.trading.auto.dto.OrderResponseDto;
import com.server.trading.auto.service.api.order.DeleteOrder;
import com.server.trading.auto.service.api.order.GetOrders;
import com.server.trading.auto.service.api.order.PostOrders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final GetOrders getOrders;
    private final PostOrders postOrders;
    private final DeleteOrder deleteOrder;

    @Transactional
    public OrderResponseDto save(OrderCreateRequestDto orderCreateRequestDto) {
        Order order = orderCreateRequestDto.toEntity();
        return new OrderResponseDto().toDto(orderRepository.save(order));
    }
}
