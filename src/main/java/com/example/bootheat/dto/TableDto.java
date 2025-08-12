package com.example.bootheat.dto;

// dto/TableDto.java
public record TableDto(Long tableId, Long boothId, Integer tableNo, Boolean active, java.time.LocalDateTime createdAt) {}
