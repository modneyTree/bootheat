package com.example.bootheat.web;

import com.example.bootheat.dto.*;
import com.example.bootheat.service.OrderService;
import com.example.bootheat.service.QueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PublicOrderController {
    private final OrderService orderService;
    private final QueryService queryService;

    // 주문 생성
    @PostMapping("/orders")
    public OrderCreatedResponse create(@Valid @RequestBody CreateOrderRequest req) {
        return orderService.createOrder(req);
    }
    // 주문 상세 조회
    @GetMapping("/orders/{orderId}")  // <- 메서드 레벨에서 "/orders/{orderId}"
    public OrderDetailManagerResponse get(@PathVariable Long orderId) {
        return queryService.getOrderDetailForManager(orderId);
    }
}
