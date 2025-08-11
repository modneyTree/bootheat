// dto/TodayStatsResponse.java
package com.example.bootheat.dto;

import java.time.LocalDate;
import java.util.List;

public record TodayStatsResponse(
        Long boothId,
        LocalDate date,
        long totalOrders,
        long totalAmount,
        Integer peakHour,                 // 0~23, 없으면 null
        List<MenuTopItem> topItems       // 기본 상위 5개
) {}
