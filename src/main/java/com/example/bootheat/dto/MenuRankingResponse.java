// dto/MenuRankingResponse.java
package com.example.bootheat.dto;

import java.time.LocalDate;
import java.util.List;

public record MenuRankingResponse(
        Long boothId,
        LocalDate date,
        String metric,            // "qty" or "amount"
        List<MenuTopItem> items
) {}
