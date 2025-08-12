// dto/CreateOrderRequest.java
package com.example.bootheat.dto;

import java.util.List;

public record CreateOrderRequest(
        Long boothId,
        Integer tableNo,
        List<Item> items,
        Payment payment
) {
    public record Item(
            Long foodId,      // = menuItemId
            String name,      // 클라 표기값 (서버는 참고만, 저장은 unitPrice만)
            Integer price,    // ★ 서버가 이 값을 그대로 unitPrice로 사용
            String imageUrl,  // 클라 표기값 (POST 응답엔 미포함)
            Integer quantity
    ) {}
    public record Payment(
            String payerName,
            Integer amount     // ★ 총 결제 금액: order.totalAmount 로 그대로 사용
    ) {}
}
