// dto/UpdateOrderStatusRequest.java
package com.example.bootheat.dto;

public record UpdateOrderStatusRequest(
        Long order_id,
        String status
) {}
