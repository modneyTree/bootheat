// src/main/java/com/example/bootheat/dto/OrderWithItemsDto.java
package com.example.bootheat.dto;

import java.time.Instant;
import java.util.List;

public record OrderWithItemsDto(
        Long orderId,
        Long boothId,
        Integer totalAmount,
        Instant createdAt,
        List<OrderItemBrief> orderItems
) {}
