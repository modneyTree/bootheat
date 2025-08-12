// web/AdminManagerUserController.java
package com.example.bootheat.web;

import com.example.bootheat.dto.CreateManagerUserRequest;
import com.example.bootheat.dto.ManagerAccountPayload;
import com.example.bootheat.dto.ManagerUserDto;
import com.example.bootheat.dto.UpdateManagerUserRequest;
import com.example.bootheat.service.ManagerUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/booths/{boothId}/manager")
@RequiredArgsConstructor
public class AdminManagerUserController {

    private final ManagerUserService service;

    // GET: 조회 (약식 JSON 반환)
    @GetMapping
    public ManagerAccountPayload get(@PathVariable Long boothId) {
        return service.getManagerPayload(boothId);
    }

    // POST: 최초 생성 (이미 있으면 에러)
    @PostMapping
    public ResponseEntity<ManagerAccountPayload> create(@PathVariable Long boothId,
                                                        @RequestBody ManagerAccountPayload body) {
        var res = service.createManagerFromPayload(boothId, body);
        return ResponseEntity.status(HttpStatus.CREATED).body(res); // 201
    }

    // PATCH: 부분 수정 (null/미포함 필드는 그대로)
    @PatchMapping
    public ManagerAccountPayload patch(@PathVariable Long boothId,
                                       @RequestBody ManagerAccountPayload body) {
        return service.updateManagerFromPayload(boothId, body);
    }
}