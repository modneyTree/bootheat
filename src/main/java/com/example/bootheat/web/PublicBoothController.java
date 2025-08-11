package com.example.bootheat.web;

import com.example.bootheat.dto.AccountInfoResponse;
import com.example.bootheat.dto.MenuItemDto;
import com.example.bootheat.service.BoothQueryService;
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
    private final BoothQueryService boothQueryService; // Booth.boothAccount 반환
    private final QueryService queryService;
    private final MenuService menuService;

    // booths API
    @GetMapping("/menus/{boothId}")
    public List<MenuItemDto> list(@PathVariable Long boothId) {
        return menuService.list(boothId);
    }
    // 단일 메뉴 조회
    @GetMapping("/menus/{boothId}/{menuItemId}")  // /api/manager/menus/1/123
    public ResponseEntity<MenuItemDto> getOne(@PathVariable Long boothId,
                                              @PathVariable Long menuItemId) {
        return ResponseEntity.ok(menuService.getOne(boothId, menuItemId));
    }
    @GetMapping("/{boothId}/account")    // /
    public AccountInfoResponse boothAccount(@PathVariable Long boothId) {
        return boothQueryService.getAccount(boothId);
    }
}