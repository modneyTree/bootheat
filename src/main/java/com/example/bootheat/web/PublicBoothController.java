package com.example.bootheat.web;

import com.example.bootheat.dto.AccountInfoResponse;
import com.example.bootheat.dto.MenuItemDto;
import com.example.bootheat.service.ManagerQueryService;
import com.example.bootheat.service.MenuService;
import com.example.bootheat.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/booths")
@RequiredArgsConstructor
class PublicBoothController {
    private final QueryService queryService;
    private final MenuService menuService;
    private final ManagerQueryService managerQueryService;

    // booths API
    @GetMapping("/{boothId}/menus")
    public List<MenuItemDto> list(@PathVariable Long boothId) {
        return menuService.list(boothId);
    }
    // 단일 메뉴 조회
    @GetMapping("/{boothId}/menus/{menuItemId}")
    public ResponseEntity<MenuItemDto> getOne(@PathVariable Long boothId,
                                              @PathVariable Long menuItemId) {
        return ResponseEntity.ok(menuService.getOne(boothId, menuItemId));
    }
    @GetMapping("/{boothId}/account")
    public AccountInfoResponse boothAccount(@PathVariable Long boothId) {
        return managerQueryService.getAccount(boothId); // ManagerUser.account 반환
    }
}