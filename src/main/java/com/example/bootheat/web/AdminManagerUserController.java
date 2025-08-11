// web/AdminManagerUserController.java
package com.example.bootheat.web;

import com.example.bootheat.dto.CreateManagerUserRequest;
import com.example.bootheat.dto.ManagerUserDto;
import com.example.bootheat.service.ManagerUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminManagerUserController {

    private final ManagerUserService managerUserService;

    // 부스 1개에 매니저 1명만
    @PostMapping("/booths/{boothId}/manager")
    public ResponseEntity<ManagerUserDto> create(@PathVariable Long boothId,
                                                 @Valid @RequestBody CreateManagerUserRequest req) {
        return ResponseEntity.ok(managerUserService.create(boothId, req));
    }

    // 현재 매니저 조회 (옵션)
    @GetMapping("/booths/{boothId}/manager")
    public ManagerUserDto get(@PathVariable Long boothId) {
        return managerUserService.getByBooth(boothId);
    }
}
