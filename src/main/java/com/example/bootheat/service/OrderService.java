package com.example.bootheat.service;

import com.example.bootheat.domain.*;
import com.example.bootheat.dto.CreateOrderRequest;
import com.example.bootheat.dto.OrderDetailResponse;
import com.example.bootheat.dto.OrderSummaryResponse;
import com.example.bootheat.repository.*;
import com.example.bootheat.support.Status;
import com.example.bootheat.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final BoothRepository boothRepo;
    private final BoothTableRepository tableRepo;
    private final MenuItemRepository menuRepo;
    private final TableVisitRepository visitRepo;       // NEW
    private final CustomerOrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final PaymentInfoRepository paymentRepo;

    @Transactional
    public OrderSummaryResponse createOrder(CreateOrderRequest req) {
        Booth booth = boothRepo.findById(req.boothId())
                .orElseThrow(() -> new IllegalArgumentException("BOOTH_NOT_FOUND"));
        BoothTable table = tableRepo.findByBooth_BoothIdAndTableNumber(req.boothId(), req.tableNo())
                .orElseThrow(() -> new IllegalArgumentException("TABLE_NOT_FOUND"));

        // 1) OPEN visit 재사용 or 새로 생성
        TableVisit visit = visitRepo
                .findFirstByTable_TableIdAndStatusOrderByStartedAtDesc(table.getTableId(), Status.OPEN)
                .orElseGet(() -> {
                    int nextNo = visitRepo.findTopByTable_TableIdOrderByVisitNoDesc(table.getTableId())
                            .map(v -> v.getVisitNo() + 1).orElse(1);
                    TableVisit v = TableVisit.builder()
                            .table(table)
                            .visitNo(nextNo)
                            .status(Status.OPEN)
                            .build();
                    return visitRepo.save(v);
                });

        // 2) 가격 확정 & 총액 계산
        int sum = 0;
        List<OrderItem> lines = new ArrayList<>();
        for (var it : req.items()) {
            MenuItem mi = menuRepo.findById(it.menuItemId())
                    .orElseThrow(() -> new IllegalArgumentException("MENU_NOT_FOUND"));
            if (!Boolean.TRUE.equals(mi.getAvailable()))
                throw new IllegalStateException("OUT_OF_STOCK");

            int lineAmount = mi.getPrice() * it.quantity();
            sum += lineAmount;

            OrderItem oi = new OrderItem();
            oi.setMenuItem(mi);
            oi.setQuantity(it.quantity());
            oi.setUnitPrice(mi.getPrice());
            lines.add(oi);
        }

        // 3) 주문 생성 (visit 연결)
        CustomerOrder order = new CustomerOrder();
        order.setBooth(booth);
        order.setTable(table);
        order.setVisit(visit);                // ★
        order.setStatus(Status.PENDING);
        order.setOrderCode(CodeGenerator.orderCode());
        order.setTotalAmount(sum);
        orderRepo.save(order);

        // 4) 라인/결제 저장
        for (OrderItem oi : lines) oi.setOrder(order);
        orderItemRepo.saveAll(lines);

        PaymentInfo pi = new PaymentInfo();
        pi.setOrder(order);
        pi.setPayerName(req.payment().payerName());
        pi.setAmount(req.payment().amount());
        paymentRepo.save(pi);

        return new OrderSummaryResponse(
                order.getOrderId(), order.getOrderCode(), order.getStatus(),
                order.getTotalAmount(),
                order.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant()
        );
    }

    @Transactional(readOnly = true)
    public OrderDetailResponse getOrder(Long orderId) {
        CustomerOrder o = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("ORDER_NOT_FOUND"));

        var items = orderItemRepo.findByOrder_OrderId(orderId).stream()
                .map(i -> new OrderDetailResponse.Line(
                        i.getMenuItem().getMenuItemId(),
                        i.getMenuItem().getName(),
                        i.getUnitPrice(),
                        i.getQuantity()))
                .toList();

        var p = paymentRepo.findByOrder_OrderId(orderId).orElse(null);

        return new OrderDetailResponse(
                o.getOrderId(), o.getOrderCode(), o.getStatus(),
                o.getTotalAmount(),
                o.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant(),
                o.getApprovedAt() == null ? null : o.getApprovedAt().atZone(ZoneId.systemDefault()).toInstant(),
                items,
                p == null ? null : new OrderDetailResponse.Payment(p.getPayerName(), p.getAmount()),
                (o.getVisit() == null) ? null : o.getVisit().getVisitId()
        );
    }

    @Transactional
    public void approve(Long orderId) {
        CustomerOrder o = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("ORDER_NOT_FOUND"));
        if (!Status.PENDING.equals(o.getStatus())) throw new IllegalStateException("INVALID_STATE");
        o.setStatus(Status.APPROVED);
        o.setApprovedAt(java.time.LocalDateTime.now());
    }

    // (선택) 테이블 비우기(visit 종료)
    @Transactional
    public void closeCurrentVisit(Long boothId, Integer tableNo) {
        BoothTable table = tableRepo.findByBooth_BoothIdAndTableNumber(boothId, tableNo)
                .orElseThrow(() -> new IllegalArgumentException("TABLE_NOT_FOUND"));
        visitRepo.findFirstByTable_TableIdAndStatusOrderByStartedAtDesc(table.getTableId(), Status.OPEN)
                .ifPresent(v -> {
                    v.setStatus(Status.CLOSED);
                    v.setClosedAt(java.time.LocalDateTime.now());
                });
    }
}
