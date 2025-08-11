package com.example.bootheat.service;

import com.example.bootheat.dto.AccountInfoResponse;
import com.example.bootheat.repository.BoothRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// service/ManagerQueryService.java (신규 or 기존 QueryService에 넣어도 됨)
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoothQueryService {
    private final BoothRepository boothRepo; // ★ 변경: managerRepo → boothRepo

    public AccountInfoResponse getAccount(Long boothId) {
        var b = boothRepo.findById(boothId)
                .orElseThrow(() -> new IllegalArgumentException("BOOTH_NOT_FOUND"));
        // booth.boothAccount가 null일 수도 있으니 널 허용
        return new AccountInfoResponse(boothId, b.getBoothAccount());
    }
}
