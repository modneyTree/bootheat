// dto/StatsSummaryResponse.java
package com.example.bootheat.dto;

public record StatsSummaryResponse(
        String date,
        long totalSales,     // 총 매출
        long orderNumbers    // 주문 건수
) {}
