package com.example.bootheat.web;

import com.example.bootheat.dto.UpdateOrderStatusRequest;
import com.example.bootheat.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class ManagerOrderController {
    private final OrderService orderService;

    // 기존 개별 엔드포인트도 유지 가능
//    @PostMapping("/orders/{orderId}/approve")
//    public ResponseEntity<Void> approve(@PathVariable Long orderId) {
//        orderService.changeStatus(orderId, "APPROVED");
//        return ResponseEntity.noContent().build();
//    }
//    @PostMapping("/orders/{orderId}/reject")
//    public ResponseEntity<Void> reject(@PathVariable Long orderId) {
//        orderService.changeStatus(orderId, "REJECTED");
//        return ResponseEntity.noContent().build();
//    }
//    @PostMapping("/orders/{orderId}/pending")
//    public ResponseEntity<Void> pending(@PathVariable Long orderId) {
//        orderService.changeStatus(orderId, "PENDING");
//        return ResponseEntity.noContent().build();
//    }
//    @PostMapping("/orders/{orderId}/finish")
//    public ResponseEntity<Void> finish(@PathVariable Long orderId) {
//        orderService.changeStatus(orderId, "FINISHED");
//        return ResponseEntity.noContent().build();
//    }

    // web/ManagerOrderController.java
    @PostMapping("/orders/{orderId}/status/{status}")
    public ResponseEntity<Void> change(
            @PathVariable Long orderId,
            @PathVariable String status,
            @RequestBody(required = false) UpdateOrderStatusRequest req
    ) {
        // 바디가 오면 유효성 체크(선택)
        if (req != null) {
            if (req.order_id() != null && !req.order_id().equals(orderId)) {
                throw new IllegalArgumentException("ORDER_ID_MISMATCH");
            }
            if (req.status() != null && !req.status().isBlank() &&
                    !req.status().equalsIgnoreCase(status)) {
                throw new IllegalArgumentException("STATUS_MISMATCH");
            }
        }
        orderService.changeStatus(orderId, status);
        return ResponseEntity.ok().build(); // 200
    }

}
