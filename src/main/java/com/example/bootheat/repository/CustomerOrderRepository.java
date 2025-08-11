package com.example.bootheat.repository;

import com.example.bootheat.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findTop10ByTable_TableIdOrderByCreatedAtDesc(Long tableId);

    // 총 주문수, 총 매출
    @Query("""
    select count(o), coalesce(sum(o.totalAmount),0)
    from CustomerOrder o
    where o.booth.boothId = :boothId
      and (o.status = 'APPROVED' or o.status = 'FINISHED')    
      and o.approvedAt >= :start and o.approvedAt < :end
""")
    Object[] sumToday(@Param("boothId") Long boothId,
                      @Param("start") LocalDateTime start,
                      @Param("end") LocalDateTime end);

    // 시간대별 주문 수 (peak hour 계산용)
    @Query("""
    select function('hour', o.approvedAt) as hh, count(o)
    from CustomerOrder o
    where o.booth.boothId = :boothId
      and (o.status = 'APPROVED' or o.status = 'FINISHED')      
      and o.approvedAt >= :start and o.approvedAt < :end
    group by function('hour', o.approvedAt)
    """)
    List<Object[]> hourlyCounts(@Param("boothId") Long boothId,
                                @Param("start") LocalDateTime start,
                                @Param("end") LocalDateTime end);
}