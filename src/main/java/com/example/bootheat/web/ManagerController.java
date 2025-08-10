package com.example.bootheat.web;

import com.example.bootheat.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final OrderService orderService;

    @PostMapping("/orders/{orderId}/approve")
    public ResponseEntity<Void> approve(@PathVariable Long orderId) {
        orderService.approve(orderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/tables/{boothId}/{tableNo}/close-visit")
    public ResponseEntity<Void> closeVisit(@PathVariable Long boothId, @PathVariable Integer tableNo) {
        orderService.closeCurrentVisit(boothId, tableNo);
        return ResponseEntity.noContent().build();
    }

}
