// src/test/java/com/example/bootheat/OrderApiTest.java
package com.example.bootheat;

import com.example.bootheat.domain.*;
import com.example.bootheat.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderApiTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @Autowired BoothRepository boothRepo;
    @Autowired BoothTableRepository tableRepo;
    @Autowired MenuItemRepository menuRepo;
    @Autowired CustomerOrderRepository orderRepo;
    @Autowired TableVisitRepository visitRepo;

    Long boothId; Long menu1;

    @BeforeEach
    void setUp() {
        orderRepo.deleteAll();
        visitRepo.deleteAll();
        tableRepo.deleteAll();
        menuRepo.deleteAll();
        boothRepo.deleteAll();
        Booth booth = TestSeedUtil.seedBoothWithTablesAndMenus(boothRepo, tableRepo, menuRepo);
        boothId = booth.getBoothId();
        menu1 = menuRepo.findAll().get(0).getMenuItemId();
    }

    @Test
    void 주문생성_조회_승인_API() throws Exception {
        // 주문 생성
        var body = Map.of(
                "boothId", boothId,
                "tableNo", 1,
                "items", List.of(Map.of("menuItemId", menu1, "quantity", 2)),
                "payment", Map.of("payerName", "테스트", "amount", 8000)
        );

        String res = mvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andReturn().getResponse().getContentAsString();

        // orderId 추출
        long orderId = om.readTree(res).get("orderId").asLong();

        // 조회
        mvc.perform(get("/api/orders/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(orderId))
                .andExpect(jsonPath("$.items[0].quantity").value(2));

        // 승인
        mvc.perform(post("/api/manager/orders/{id}/approve", orderId))
                .andExpect(status().isNoContent());

        // 재조회 → APPROVED
        mvc.perform(get("/api/orders/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"))
                .andExpect(jsonPath("$.approvedAt").exists());
    }
}
