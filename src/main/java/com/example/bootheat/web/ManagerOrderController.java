package com.example.bootheat.web;

import com.example.bootheat.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class ManagerOrderController {
    private final OrderService orderService;

    @PostMapping("/orders/{orderId}/approve")
    public ResponseEntity<Void> approve(@PathVariable Long orderId) {
        orderService.approve(orderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/orders/{orderId}/finish")
    public ResponseEntity<Void> finish(@PathVariable Long orderId) {
        orderService.finish(orderId);
        return ResponseEntity.noContent().build();
    }
}
