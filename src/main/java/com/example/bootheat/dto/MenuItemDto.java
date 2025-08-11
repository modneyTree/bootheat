// dto/MenuItemDto.java
package com.example.bootheat.dto;

public record MenuItemDto(
        Long menuItemId, Long boothId, String name, Integer price, Boolean available,
        String modelUrl, String previewImage, String description,
        String category // "FOOD" | "DRINK"
) {}