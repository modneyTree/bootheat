// dto/TableListItem.java
package com.example.bootheat.dto;

public record TableListItem(
        Long tableId,
        Integer tableNumber,
        Boolean active,
        String tableVisit // "OPEN" | "CLOSED"
) {}
