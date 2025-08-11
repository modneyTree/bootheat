// web/ManagerMenuController.java
package com.example.bootheat.web;

import com.example.bootheat.dto.CreateMenuRequest;
import com.example.bootheat.dto.MenuItemDto;
import com.example.bootheat.dto.ToggleResponse;
import com.example.bootheat.dto.UpdateMenuRequest;
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
    @PostMapping("/menus")  // /api/manager/menus
    public ResponseEntity<MenuItemDto> create(@Valid @RequestBody CreateMenuRequest req) {
        return ResponseEntity.ok(menuService.create(req));
    }

    // 수정 (부분 업데이트)
    @PutMapping("/menus/{menuItemId}")  // /api/manager/menus/{menuItemId}
    public ResponseEntity<MenuItemDto> update(@PathVariable Long menuItemId,
                                              @Valid @RequestBody UpdateMenuRequest req) {
        return ResponseEntity.ok(menuService.update(menuItemId, req));
    }

    // 품절 토글
    @PostMapping("/menus/{menuItemId}/toggle-available")    // /api/manager/menus/{menuItemId}/toggle-available
    public ResponseEntity<ToggleResponse> toggle(@PathVariable Long menuItemId) {
        boolean available = menuService.toggleAvailable(menuItemId);
        return ResponseEntity.ok(new ToggleResponse(menuItemId, available));
    }

    // 삭제(옵션)
    @DeleteMapping("/menus/{menuItemId}")   // /api/manager/menus/{menuItemId}
    public ResponseEntity<Void> delete(@PathVariable Long menuItemId) {
        menuService.delete(menuItemId);
        return ResponseEntity.noContent().build();
    }
}
