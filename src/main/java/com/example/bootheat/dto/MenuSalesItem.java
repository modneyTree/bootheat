// dto/MenuSalesItem.java
package com.example.bootheat.dto;

public record MenuSalesItem(
        Long menuItemId,
        String name,
        long totalSales
) {}
