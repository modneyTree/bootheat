// web/ManagerStatsController.java
package com.example.bootheat.web;

import com.example.bootheat.dto.*;
import com.example.bootheat.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class ManagerStatsController {

    private final StatsService statsService;

    // GET /api/manager/booths/{boothId}/menus/{menuItemId}/metrics/total-orders
    @GetMapping("/booths/{boothId}/menus/{menuItemId}/metrics/total-orders")
    public MenuTotalOrdersNewResponse totalOrders(@PathVariable Long boothId,
                                                  @PathVariable Long menuItemId) {
        long total = statsService.totalOrdersForMenu(boothId, menuItemId);
        return new MenuTotalOrdersNewResponse(menuItemId, total);
    }


    // GET /api/manager/booths/{boothId}/stats/menu-sales?date=YYYY-MM-DD
    @GetMapping("/booths/{boothId}/stats/menu-sales")
    public java.util.List<MenuSalesItem> menuSales(@PathVariable Long boothId,
                                                   @RequestParam(required = false)
                                                   @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
                                                   java.time.LocalDate date) {
        return statsService.menuSalesItems(boothId, date);
    }

    // web/ManagerStatsController.java (엔드포인트 추가/교체)
    @GetMapping("/booths/{boothId}/stats/date/{date}")
    public StatsSummaryResponse summaryByDate(@PathVariable Long boothId,
                                              @PathVariable @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
                                              java.time.LocalDate date) {
        return statsService.statsSummaryByDate(boothId, date);
    }



}
