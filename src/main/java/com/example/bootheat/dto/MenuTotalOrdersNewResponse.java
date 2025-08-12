// dto/MenuTotalOrdersNewResponse.java
package com.example.bootheat.dto;

public record MenuTotalOrdersNewResponse(
        Long menuItemId,
        Long totalOrderQuantity
) {}
