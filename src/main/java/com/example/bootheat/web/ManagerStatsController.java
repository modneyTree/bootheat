// web/ManagerStatsController.java
package com.example.bootheat.web;

import com.example.bootheat.dto.MenuRankingResponse;
import com.example.bootheat.dto.TableContextResponse;
import com.example.bootheat.dto.TodayStatsResponse;
import com.example.bootheat.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class ManagerStatsController {

    private final StatsService statsService;

    // 오늘 현황 (총 주문수/매출, Top N, 피크아워)
    // GET /api/manager/stats/today?boothId=1&top=5
    @GetMapping("/stats/today")
    public TodayStatsResponse today(@RequestParam Long boothId,
                                    @RequestParam(name="top", defaultValue = "5") int top) {
        return statsService.todayStats(boothId, Math.max(1, Math.min(top, 20)));
    }

    // 메뉴 랭킹 (오늘)
    // GET /api/manager/rankings/menu?boothId=1&metric=qty|amount&limit=5
    @GetMapping("/rankings/menu")
    public MenuRankingResponse ranking(@RequestParam Long boothId,
                                       @RequestParam(defaultValue = "qty") String metric,
                                       @RequestParam(defaultValue = "5") int limit) {
        String m = "amount".equalsIgnoreCase(metric) ? "amount" : "qty";
        int lim = Math.max(1, Math.min(limit, 50));
        return statsService.ranking(boothId, m, lim);
    }


}
