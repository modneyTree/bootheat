package com.example.bootheat.web;

import com.example.bootheat.dto.TableContextResponse;
import com.example.bootheat.service.OrderService;
import com.example.bootheat.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
class ManagerTableController {
    private final OrderService orderService;
    private final QueryService queryService;

    @PostMapping("/tables/{boothId}/{tableNo}/close-visit")
    public ResponseEntity<Void> closeVisit(@PathVariable Long boothId, @PathVariable Integer tableNo) {
        orderService.closeCurrentVisit(boothId, tableNo);   // table_visit status를 CLOSED로 변경
        return ResponseEntity.noContent().build();
    }
}