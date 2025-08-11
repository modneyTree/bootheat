package com.example.bootheat.web;

import com.example.bootheat.dto.CreateOrderRequest;
import com.example.bootheat.dto.OrderDetailResponse;
import com.example.bootheat.dto.OrderSummaryResponse;
import com.example.bootheat.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PublicOrderController {
    private final OrderService orderService;

    // 주문 생성
    @PostMapping("/orders")
    public OrderSummaryResponse create(@Valid @RequestBody CreateOrderRequest req) {
        return orderService.createOrder(req);
    }
    // 주문 상세 조회
    @GetMapping("/orders/{orderId}")
    public OrderDetailResponse order(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }
}
