package com.example.bootheat.dto;

import java.time.Instant;

public record ManagerUserDto(
        Long managerId, Long boothId, String username, String role,
        String accountBank, String accountNo, String accountHolder
) {}