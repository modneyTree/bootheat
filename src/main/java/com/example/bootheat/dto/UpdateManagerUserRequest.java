package com.example.bootheat.dto;

public record UpdateManagerUserRequest(
        String username,
        String password,
        String role,
        String bank,
        String account,
        String accountHolder
) {}