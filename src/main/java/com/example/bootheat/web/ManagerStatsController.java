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
    // 기능: 특정 메뉴 아이템의 총 주문 수를 조회
    @GetMapping("/booths/{boothId}/menus/{menuItemId}/metrics/total-orders")
    public MenuTotalOrdersNewResponse totalOrders(@PathVariable Long boothId,
                                                  @PathVariable Long menuItemId) {
        long total = statsService.totalOrdersForMenu(boothId, menuItemId);
        return new MenuTotalOrdersNewResponse(menuItemId, total);
    }


    // GET /api/manager/booths/{boothId}/stats/menu-sales?date=YYYY-MM-DD
    // 기능: 특정 날짜의 메뉴 판매 통계 조회
    @GetMapping("/booths/{boothId}/stats/menu-sales")
    public java.util.List<MenuSalesItem> menuSales(@PathVariable Long boothId,
                                                   @RequestParam(required = false)
                                                   @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
                                                   java.time.LocalDate date) {
        return statsService.menuSalesItems(boothId, date);
    }

    // web/ManagerStatsController.java (엔드포인트 추가/교체)
    // 기능: 날짜별 통계 조회
    @GetMapping("/booths/{boothId}/stats/date/{date}")
    public StatsSummaryResponse summaryByDate(@PathVariable Long boothId,
                                              @PathVariable @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
                                              java.time.LocalDate date) {
        return statsService.statsSummaryByDate(boothId, date);
    }

    // GET /api/manager/rankings/menu?boothId=1&metric=qty|amount&limit=5
    // 기능: 메뉴 판매 순위 조회
    @GetMapping("/rankings/menu")
    public MenuRankingResponse ranking(@RequestParam Long boothId,
                                       @RequestParam(defaultValue = "qty") String metric,
                                       @RequestParam(defaultValue = "5") int limit) {
        return statsService.ranking(boothId, metric, limit);
    }
}
