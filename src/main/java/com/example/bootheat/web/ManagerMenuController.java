// web/ManagerMenuController.java
package com.example.bootheat.web;

import com.example.bootheat.dto.*;
import com.example.bootheat.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class ManagerMenuController {

    private final MenuService menuService;

    // 생성
    // POST /api/manager/booths/{boothId}/menus
    // POST /api/manager/booths/{boothId}/menus
    @PostMapping("/booths/{boothId}/menus")
    public ResponseEntity<Void> create(@PathVariable Long boothId,
                                       @Valid @RequestBody CreateMenuRequest req) {
        var fixed = new CreateMenuRequest(
                boothId, req.name(), req.price(), req.available(),
                req.modelUrl(), req.previewImage(), req.description(), req.category());
        menuService.create(fixed);
        return ResponseEntity.status(201).build(); // 201 Created
    }

    // PATCH /api/manager/booths/{boothId}/menus/{menuItemId}
    @PatchMapping("/booths/{boothId}/menus/{menuItemId}")
    public ResponseEntity<Void> update(@PathVariable Long boothId,
                                       @PathVariable Long menuItemId,
                                       @Valid @RequestBody UpdateMenuRequest req) {
        menuService.updateScoped(boothId, menuItemId, req);
        return ResponseEntity.ok().build(); // 200 OK
    }

    // DELETE /api/manager/booths/{boothId}/menus/{menuItemId}
    @DeleteMapping("/booths/{boothId}/menus/{menuItemId}")
    public ResponseEntity<Void> delete(@PathVariable Long boothId, @PathVariable Long menuItemId) {
        menuService.deleteScoped(boothId, menuItemId);
        return ResponseEntity.ok().build(); // 200 OK
    }

    // web/ManagerMenuController.java (토글 엔드포인트 교체)
    @PostMapping("/menus/{menuItemId}/toggle-available")
    public ResponseEntity<Void> toggle(@PathVariable Long menuItemId,
                                       @RequestBody(required = false) ToggleAvailableRequest req) {
        if (req != null && req.available() != null) {
            menuService.setAvailable(menuItemId, req.available());
        } else {
            menuService.toggleAvailable(menuItemId); // 바디 없으면 기존 토글
        }
        return ResponseEntity.ok().build(); // 200 OK
    }

}
