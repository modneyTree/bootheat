package com.example.bootheat.dto;

import java.time.Instant;
import java.util.List;

public record TableContextResponse(
        Long boothId,
        Integer tableNo,
        Visit currentVisit,         // 없으면 null
        List<OrderRow> recentOrders // 최신순
) {
    public record Visit(Long visitId, Integer visitNo, String status, Instant startedAt, Instant closedAt) {}
    public record OrderRow(Long orderId, String orderCode, String status,
                           Integer totalAmount, Instant createdAt, Instant approvedAt, Long visitId) {}
}
