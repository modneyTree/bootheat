// service/StatsService.java
package com.example.bootheat.service;

import com.example.bootheat.dto.*;
import com.example.bootheat.repository.CustomerOrderRepository;
import com.example.bootheat.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsService {

    private final CustomerOrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;

    private static class Range {
        final LocalDate date;
        final LocalDateTime start;
        final LocalDateTime end;
        Range(LocalDate date, ZoneId zone) {
            this.date = date;
            this.start = date.atStartOfDay();
            this.end = this.start.plusDays(1);
        }
    }
    private Range todayRange() {
        var zone = ZoneId.systemDefault();
        var date = LocalDate.now(zone);
        return new Range(date, zone);
    }

    public TodayStatsResponse todayStats(Long boothId, int topN) {
        var r = todayRange();

        // 총 주문수/총액
        Object[] sum = orderRepo.sumToday(boothId, r.start, r.end);
        long totalOrders = (sum[0] == null) ? 0L : ((Number) sum[0]).longValue();
        long totalAmount = (sum[1] == null) ? 0L : ((Number) sum[1]).longValue();

        // 시간대별 → 피크아워
        Integer peakHour = null;
        var buckets = orderRepo.hourlyCounts(boothId, r.start, r.end);
        if (!buckets.isEmpty()) {
            peakHour = buckets.stream()
                    .max(Comparator.comparingLong(o -> ((Number)o[1]).longValue()))
                    .map(o -> ((Number) o[0]).intValue())
                    .orElse(null);
        }

        // 메뉴 Top N (qty 기준 기본 정렬)
        var rows = orderItemRepo.aggregateMenuToday(boothId, r.start, r.end);
        List<MenuTopItem> all = rows.stream()
                .map(o -> new MenuTopItem(
                        ((Number)o[0]).longValue(),
                        (String) o[1],
                        ((Number)o[2]).longValue(),
                        ((Number)o[3]).longValue()
                ))
                .sorted(Comparator.comparingLong(MenuTopItem::qty).reversed()
                        .thenComparingLong(MenuTopItem::amount).reversed())
                .toList();
        List<MenuTopItem> top = all.size() > topN ? all.subList(0, topN) : all;

        return new TodayStatsResponse(boothId, r.date, totalOrders, totalAmount, peakHour, top);
    }

    public MenuRankingResponse ranking(Long boothId, String metric, int limit) {
        var r = todayRange();
        var rows = orderItemRepo.aggregateMenuToday(boothId, r.start, r.end);
        Comparator<MenuTopItem> cmp = "amount".equalsIgnoreCase(metric)
                ? Comparator.comparingLong(MenuTopItem::amount).reversed()
                : Comparator.comparingLong(MenuTopItem::qty).reversed();

        List<MenuTopItem> all = rows.stream()
                .map(o -> new MenuTopItem(
                        ((Number)o[0]).longValue(),
                        (String) o[1],
                        ((Number)o[2]).longValue(),
                        ((Number)o[3]).longValue()
                ))
                .sorted(cmp.thenComparing(MenuTopItem::name))
                .toList();

        List<MenuTopItem> items = all.size() > limit ? all.subList(0, limit) : all;
        return new MenuRankingResponse(boothId, r.date, metric, items);
    }

    @Transactional(readOnly = true)
    public TodayStatsResponse statsByDate(Long boothId, LocalDate date) {
        var start = date.atStartOfDay();
        var end = start.plusDays(1);

        Object[] sum = orderRepo.sumBetween(boothId, start, end);
        long totalOrders = sum[0]==null?0L:((Number)sum[0]).longValue();
        long totalAmount = sum[1]==null?0L:((Number)sum[1]).longValue();

        Integer peakHour = null;
        var buckets = orderRepo.hourlyCountsBetween(boothId, start, end);
        if (!buckets.isEmpty()) {
            peakHour = buckets.stream()
                    .max(java.util.Comparator.comparingLong(o -> ((Number)o[1]).longValue()))
                    .map(o -> ((Number)o[0]).intValue())
                    .orElse(null);
        }

        var rows = orderItemRepo.aggregateMenuBetween(boothId, start, end);
        var all = rows.stream()
                .map(o -> new MenuTopItem(((Number)o[0]).longValue(), (String)o[1],
                        ((Number)o[2]).longValue(), ((Number)o[3]).longValue()))
                .sorted(java.util.Comparator.comparingLong(MenuTopItem::qty).reversed()
                        .thenComparingLong(MenuTopItem::amount).reversed())
                .toList();
        var top = all.size()>5 ? all.subList(0,5) : all;
        return new TodayStatsResponse(boothId, date, totalOrders, totalAmount, peakHour, top);
    }

    @Transactional(readOnly = true)
    public long totalOrdersForMenu(Long boothId, Long menuItemId) {
        return orderItemRepo.totalQtyByBoothAndMenu(boothId, menuItemId);
    }

    @Transactional(readOnly = true)
    public MenuRankingResponse menuSales(Long boothId, LocalDate date) {
        var d = (date==null) ? java.time.LocalDate.now() : date;
        var start = d.atStartOfDay();
        var end = start.plusDays(1);
        var rows = orderItemRepo.aggregateMenuBetween(boothId, start, end);
        var items = rows.stream()
                .map(o -> new MenuTopItem(((Number)o[0]).longValue(), (String)o[1],
                        ((Number)o[2]).longValue(), ((Number)o[3]).longValue()))
                .sorted(java.util.Comparator.comparingLong(MenuTopItem::qty).reversed()
                        .thenComparing(MenuTopItem::name))
                .toList();
        return new MenuRankingResponse(boothId, d, "qty", items);
    }
    // service/StatsService.java (메서드 추가)
    @Transactional(readOnly = true)
    public StatsSummaryResponse statsSummaryByDate(Long boothId, java.time.LocalDate date) {
        var start = date.atStartOfDay();
        var end = start.plusDays(1);
        Object[] sum = orderRepo.sumBetween(boothId, start, end);
        long orders = (sum[0]==null) ? 0L : ((Number)sum[0]).longValue();
        long sales  = (sum[1]==null) ? 0L : ((Number)sum[1]).longValue();
        return new StatsSummaryResponse(date.toString(), sales, orders);
    }

    @Transactional(readOnly = true)
    public java.util.List<MenuSalesItem> menuSalesItems(Long boothId, java.time.LocalDate date) {
        var d = (date==null) ? java.time.LocalDate.now() : date;
        var start = d.atStartOfDay();
        var end = start.plusDays(1);
        var rows = orderItemRepo.aggregateMenuBetween(boothId, start, end);
        return rows.stream()
                .map(o -> new MenuSalesItem(
                        ((Number)o[0]).longValue(),
                        (String) o[1],
                        ((Number)o[3]).longValue() // amount
                ))
                .sorted(java.util.Comparator.comparingLong(MenuSalesItem::totalSales).reversed())
                .toList();
    }

}
