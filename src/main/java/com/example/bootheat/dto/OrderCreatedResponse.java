// dto/OrderCreatedResponse.java
package com.example.bootheat.dto;

import java.time.Instant;

public record OrderCreatedResponse(
        Long orderId,
        String status,   // "PENDDING" (프론트 요구 스펙)
        Integer amount,
        Instant createdAt
) {}
