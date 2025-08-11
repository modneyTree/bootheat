package com.example.bootheat.dto;

import jakarta.validation.constraints.*;

public record CreateManagerUserRequest(
        @NotBlank @Size(max = 50) String username,
        @NotBlank @Size(min = 4, max = 100) String password,
        @Size(max = 20) String role // null/blank → "MANAGER"
) {}