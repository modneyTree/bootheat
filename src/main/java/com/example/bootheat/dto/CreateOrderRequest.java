package com.example.bootheat.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public record CreateOrderRequest(
        @NotNull Long boothId,
        @NotNull @Min(1) Integer tableNo,
        @NotNull @Size(min=1) List<Item> items,
        @NotNull Payment payment
) {
    public record Item(@NotNull Long menuItemId, @NotNull @Min(1) Integer quantity) {}
    public record Payment(@NotBlank String payerName, @NotNull @Min(0) Integer amount) {}
}
