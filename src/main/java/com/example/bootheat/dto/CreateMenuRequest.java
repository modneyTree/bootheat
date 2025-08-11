// dto/CreateMenuRequest.java
package com.example.bootheat.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateMenuRequest(
        @NotNull Long boothId,
        @NotBlank String name,
        @NotNull @Min(0) Integer price,
        Boolean available,
        String modelUrl,
        String previewImage,
        String description,
        @NotBlank String category // "FOOD" | "DRINK"
) {}