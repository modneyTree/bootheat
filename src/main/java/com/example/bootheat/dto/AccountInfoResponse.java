package com.example.bootheat.dto;

public record AccountInfoResponse(
        String bank,
        String account,
        String accountHolder
) {}