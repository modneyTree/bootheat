package com.example.bootheat.repository;

import com.example.bootheat.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder_OrderId(Long orderId);

    // 메뉴별 수량/매출 집계 (오늘, APPROVED만)
    @Query("""
        select oi.menuItem.menuItemId,
               oi.menuItem.name,
               sum(oi.quantity) as qty,
               sum(oi.quantity * oi.unitPrice) as amount
        from OrderItem oi
          join oi.order o
        where o.booth.boothId = :boothId
          and (o.status = 'APPROVED' or o.status = 'FINISHED')
          and o.approvedAt >= :start and o.approvedAt < :end
        group by oi.menuItem.menuItemId, oi.menuItem.name
    """)
    List<Object[]> aggregateMenuToday(@Param("boothId") Long boothId,
                                      @Param("start") LocalDateTime start,
                                      @Param("end") LocalDateTime end);
}
