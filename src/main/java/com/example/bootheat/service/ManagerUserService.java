// service/ManagerUserService.java
package com.example.bootheat.service;

import com.example.bootheat.domain.Booth;
import com.example.bootheat.domain.ManagerUser;
import com.example.bootheat.dto.CreateManagerUserRequest;
import com.example.bootheat.dto.ManagerAccountPayload;
import com.example.bootheat.dto.ManagerUserDto;
import com.example.bootheat.dto.UpdateManagerUserRequest;
import com.example.bootheat.repository.BoothRepository;
import com.example.bootheat.repository.ManagerUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ManagerUserService {

    private final BoothRepository boothRepo;
    private final ManagerUserRepository managerRepo;
    private final PasswordEncoder passwordEncoder;

    public ManagerUserDto create(Long boothId, CreateManagerUserRequest req) {
        var booth = boothRepo.findById(boothId)
                .orElseThrow(() -> new IllegalArgumentException("BOOTH_NOT_FOUND"));
        if (managerRepo.existsByBooth_BoothId(boothId))
            throw new IllegalStateException("MANAGER_ALREADY_EXISTS");
        if (req.username()!=null && managerRepo.existsByUsername(req.username()))
            throw new IllegalArgumentException("USERNAME_TAKEN");

        String role = (req.role()==null || req.role().isBlank()) ? "MANAGER" : req.role().toUpperCase();
        String rawPw = (req.password()==null || req.password().isBlank()) ? "!temp!" : req.password();

        var mu = ManagerUser.builder()
                .booth(booth)
                .username(req.username())
                .passwordHash(passwordEncoder.encode(rawPw))
                .role(role)
                .accountBank(req.bank())
                .accountNo(req.account())
                .accountHolder(req.accountHolder())
                .build();

        managerRepo.save(mu);
        return toDto(mu);
    }

    @Transactional(readOnly = true)
    public ManagerUserDto getByBooth(Long boothId) {
        var mu = managerRepo.findByBooth_BoothId(boothId)
                .orElseThrow(() -> new IllegalArgumentException("MANAGER_NOT_FOUND"));
        return toDto(mu);
    }

    @Transactional
    public ManagerUserDto updateForBooth(Long boothId, UpdateManagerUserRequest req) {
        var mu = managerRepo.findByBooth_BoothId(boothId)
                .orElseThrow(() -> new IllegalArgumentException("MANAGER_NOT_FOUND"));

        if (req.username()!=null && !req.username().isBlank()
                && !req.username().equals(mu.getUsername())) {
            if (managerRepo.existsByUsername(req.username()))
                throw new IllegalArgumentException("USERNAME_TAKEN");
            mu.setUsername(req.username());
        }
        if (req.password()!=null && !req.password().isBlank())
            mu.setPasswordHash(passwordEncoder.encode(req.password()));
        if (req.role()!=null && !req.role().isBlank())
            mu.setRole(req.role().toUpperCase());
        if (req.bank()!=null)          mu.setAccountBank(req.bank());
        if (req.account()!=null)       mu.setAccountNo(req.account());
        if (req.accountHolder()!=null) mu.setAccountHolder(req.accountHolder());

        return toDto(mu);
    }

    private ManagerUserDto toDto(ManagerUser m) {
        return new ManagerUserDto(
                m.getManagerId(), m.getBooth().getBoothId(), m.getUsername(), m.getRole(),
                m.getAccountBank(), m.getAccountNo(), m.getAccountHolder()
        );
    }

    public ManagerAccountPayload getManagerPayload(Long boothId) {
        var mu = managerRepo.findByBooth_BoothId(boothId)
                .orElseThrow(() -> new IllegalArgumentException("MANAGER_NOT_FOUND"));
        return ManagerAccountPayload.from(mu);
    }

    public ManagerAccountPayload createManagerFromPayload(Long boothId, ManagerAccountPayload p) {
        Booth booth = boothRepo.findById(boothId)
                .orElseThrow(() -> new IllegalArgumentException("BOOTH_NOT_FOUND"));

        if (managerRepo.existsByBooth_BoothId(boothId))
            throw new IllegalStateException("MANAGER_ALREADY_EXISTS");

        if (p.username()==null || p.username().isBlank()
                || p.accountBank()==null || p.accountBank().isBlank()
                || p.accountNo()==null || p.accountNo().isBlank()
                || p.accountHolder()==null || p.accountHolder().isBlank())
            throw new IllegalArgumentException("REQUIRED_FIELDS_MISSING");

        if (managerRepo.existsByUsername(p.username()))
            throw new IllegalArgumentException("USERNAME_TAKEN");

        var mu = ManagerUser.builder()
                .booth(booth)
                .username(p.username())
                .passwordHash(passwordEncoder.encode("!" + UUID.randomUUID())) // 기본 임시 비밀번호
                .role("MANAGER") // 기본 역할
                .accountBank(p.accountBank())
                .accountNo(p.accountNo())
                .accountHolder(p.accountHolder())
                .build();

        managerRepo.save(mu);
        return ManagerAccountPayload.from(mu);
    }

    public ManagerAccountPayload updateManagerFromPayload(Long boothId, ManagerAccountPayload p) {
        var mu = managerRepo.findByBooth_BoothId(boothId)
                .orElseThrow(() -> new IllegalArgumentException("MANAGER_NOT_FOUND"));

        if (p.username()!=null && !p.username().isBlank()
                && !p.username().equals(mu.getUsername())) {
            if (managerRepo.existsByUsername(p.username()))
                throw new IllegalArgumentException("USERNAME_TAKEN");
            mu.setUsername(p.username());
        }
        if (p.accountBank()!=null)   mu.setAccountBank(p.accountBank());
        if (p.accountNo()!=null)     mu.setAccountNo(p.accountNo());
        if (p.accountHolder()!=null) mu.setAccountHolder(p.accountHolder());

        return ManagerAccountPayload.from(mu);
    }
}
