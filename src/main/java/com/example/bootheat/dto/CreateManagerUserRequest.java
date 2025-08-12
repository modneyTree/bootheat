package com.example.bootheat.dto;

import jakarta.validation.constraints.*;

public record CreateManagerUserRequest(
        @NotBlank String username,
        String password,             // (옵션) 없으면 임시값 처리
        @Size(max=20) String role,   // null → "MANAGER"
        @NotBlank String bank,
        @NotBlank String account,    // = accountNo
        @NotBlank String accountHolder
) {}