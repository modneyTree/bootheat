package com.example.bootheat.service;

import com.example.bootheat.dto.AccountInfoResponse;
import com.example.bootheat.repository.BoothRepository;
import com.example.bootheat.repository.ManagerUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// service/ManagerQueryService.java (신규 or 기존 QueryService에 넣어도 됨)
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerQueryService {
    private final ManagerUserRepository managerRepo;

    @Transactional(readOnly = true)
    public AccountInfoResponse getAccount(Long boothId) {
        var m = managerRepo.findByBooth_BoothId(boothId)
                .orElseThrow(() -> new IllegalArgumentException("MANAGER_NOT_FOUND"));
        return new AccountInfoResponse(
                m.getAccountBank(),
                m.getAccountNo(),
                m.getAccountHolder()
        );
    }
}
