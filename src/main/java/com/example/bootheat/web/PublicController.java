package com.example.bootheat.web;

import com.example.bootheat.dto.CreateOrderRequest;
import com.example.bootheat.dto.OrderDetailResponse;
import com.example.bootheat.dto.OrderSummaryResponse;
import com.example.bootheat.dto.TableInfoResponse;
import com.example.bootheat.service.OrderService;
import com.example.bootheat.service.QueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PublicController {
    private final QueryService queryService;
    private final OrderService orderService;

    @GetMapping("/booths/{boothId}/tables/{tableNo}")
    public TableInfoResponse table(@PathVariable Long boothId, @PathVariable Integer tableNo) {
        return queryService.getTableInfo(boothId, tableNo);
    }

    @PostMapping("/orders")
    public OrderSummaryResponse create(@Valid @RequestBody CreateOrderRequest req) {
        return orderService.createOrder(req);
    }

    @GetMapping("/orders/{orderId}")
    public OrderDetailResponse order(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }
}
