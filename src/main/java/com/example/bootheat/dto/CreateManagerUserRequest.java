package com.example.bootheat.dto;

import jakarta.validation.constraints.*;

public record CreateManagerUserRequest(
        String username,
        String password,
        String role,
        String bank,          // ★ NEW
        String account,       // 예전 필드 (선택)
        String accountHolder  // ★ NEW
) {}