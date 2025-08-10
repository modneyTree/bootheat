// src/test/java/com/example/bootheat/OrderServiceTest.java
package com.example.bootheat;

import com.example.bootheat.domain.*;
import com.example.bootheat.dto.CreateOrderRequest;
import com.example.bootheat.dto.OrderDetailResponse;
import com.example.bootheat.dto.OrderSummaryResponse;
import com.example.bootheat.repository.*;
import com.example.bootheat.service.OrderService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired BoothRepository boothRepo;
    @Autowired BoothTableRepository tableRepo;
    @Autowired MenuItemRepository menuRepo;
    @Autowired CustomerOrderRepository orderRepo;
    @Autowired PaymentInfoRepository paymentRepo;
    @Autowired OrderItemRepository orderItemRepo;
    @Autowired TableVisitRepository visitRepo;

    Long boothId;

    @BeforeEach
    void setUp() {
        orderItemRepo.deleteAll();
        paymentRepo.deleteAll();
        orderRepo.deleteAll();
        visitRepo.deleteAll();
        tableRepo.deleteAll();
        menuRepo.deleteAll();
        boothRepo.deleteAll();

        Booth booth = TestSeedUtil.seedBoothWithTablesAndMenus(boothRepo, tableRepo, menuRepo);
        boothId = booth.getBoothId();
    }

    @Test
    void 주문_생성시_visit_자동생성_그리고_재사용() {
        // given
        Long menuId = menuRepo.findAll().get(0).getMenuItemId();

        CreateOrderRequest req1 = new CreateOrderRequest(
                boothId, 1,
                List.of(new CreateOrderRequest.Item(menuId, 1)),
                new CreateOrderRequest.Payment("테스트", 4000)
        );

        // when
        OrderSummaryResponse o1 = orderService.createOrder(req1);

        // then: visit 생성 확인
        CustomerOrder saved1 = orderRepo.findById(o1.orderId()).orElseThrow();
        Long visitId1 = saved1.getVisit().getVisitId();
        assertThat(visitId1).isNotNull();

        // 같은 테이블로 한 번 더
        CreateOrderRequest req2 = new CreateOrderRequest(
                boothId, 1,
                List.of(new CreateOrderRequest.Item(menuId, 2)),
                new CreateOrderRequest.Payment("테스트", 8000)
        );
        OrderSummaryResponse o2 = orderService.createOrder(req2);
        CustomerOrder saved2 = orderRepo.findById(o2.orderId()).orElseThrow();

        // then: 같은 OPEN visit 재사용
        assertThat(saved2.getVisit().getVisitId()).isEqualTo(visitId1);
    }

    @Test
    void 주문_조회_그리고_승인() {
        Long menuId = menuRepo.findAll().get(1).getMenuItemId();

        var req = new CreateOrderRequest(
                boothId, 2,
                List.of(new CreateOrderRequest.Item(menuId, 1)),
                new CreateOrderRequest.Payment("손님", 5000)
        );
        OrderSummaryResponse summary = orderService.createOrder(req);

        // 조회
        OrderDetailResponse detail = orderService.getOrder(summary.orderId());
        assertThat(detail.status()).isEqualTo("PENDING");
        assertThat(detail.items()).hasSize(1);

        // 승인
        orderService.approve(summary.orderId());
        OrderDetailResponse after = orderService.getOrder(summary.orderId());
        assertThat(after.status()).isEqualTo("APPROVED");
        assertThat(after.approvedAt()).isNotNull();
    }
}
