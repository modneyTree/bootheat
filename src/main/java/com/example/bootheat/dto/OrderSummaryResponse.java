package com.example.bootheat.dto;

import java.time.Instant;

public record OrderSummaryResponse(
        Long orderId,
        String orderCode,
        String status,
        Integer totalAmount,
        Instant createdAt
) {}
