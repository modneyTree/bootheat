// service/ManagerUserService.java
package com.example.bootheat.service;

import com.example.bootheat.domain.Booth;
import com.example.bootheat.domain.ManagerUser;
import com.example.bootheat.dto.CreateManagerUserRequest;
import com.example.bootheat.dto.ManagerUserDto;
import com.example.bootheat.repository.BoothRepository;
import com.example.bootheat.repository.ManagerUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Transactional
public class ManagerUserService {

    private final BoothRepository boothRepo;
    private final ManagerUserRepository managerRepo;
    private final PasswordEncoder passwordEncoder;

    public ManagerUserDto create(Long boothId, CreateManagerUserRequest req) {
        Booth booth = boothRepo.findById(boothId)
                .orElseThrow(() -> new IllegalArgumentException("BOOTH_NOT_FOUND"));
        if (managerRepo.existsByBooth_BoothId(boothId))
            throw new IllegalArgumentException("MANAGER_ALREADY_EXISTS");
        if (managerRepo.existsByUsername(req.username()))
            throw new IllegalArgumentException("USERNAME_TAKEN");

        String role = (req.role()==null || req.role().isBlank()) ? "MANAGER" : req.role().toUpperCase();

        ManagerUser mu = ManagerUser.builder()
                .booth(booth)
                .username(req.username())
                .passwordHash(passwordEncoder.encode(req.password()))
                .role(role)
                .account(req.account())               // (구)문자열 보관은 선택
                .accountBank(req.bank())
                .accountNo(req.account())
                .accountHolder(req.accountHolder())
                .build();

        managerRepo.save(mu);
        return toDto(mu);
    }

    @Transactional(readOnly = true)
    public ManagerUserDto getByBooth(Long boothId) {
        ManagerUser mu = managerRepo.findByBooth_BoothId(boothId)
                .orElseThrow(() -> new IllegalArgumentException("MANAGER_NOT_FOUND"));
        return toDto(mu);
    }

    private ManagerUserDto toDto(ManagerUser m) {
        return new ManagerUserDto(
                m.getManagerId(),
                m.getBooth().getBoothId(),
                m.getUsername(),
                m.getRole(),
                m.getAccount(), // ★
                m.getCreatedAt()==null ? null : m.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant()
        );
    }
}
