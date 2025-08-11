// service/MenuService.java
package com.example.bootheat.service;

import com.example.bootheat.domain.Booth;
import com.example.bootheat.domain.MenuItem;
import com.example.bootheat.dto.CreateMenuRequest;
import com.example.bootheat.dto.MenuItemDto;
import com.example.bootheat.dto.UpdateMenuRequest;
import com.example.bootheat.repository.BoothRepository;
import com.example.bootheat.repository.MenuItemRepository;
import com.example.bootheat.support.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
@Transactional
public class MenuService {

    private final BoothRepository boothRepo;
    private final MenuItemRepository menuRepo;

    @Transactional(readOnly = true)
    public MenuItemDto getOne(Long boothId, Long menuItemId) {
        MenuItem m = menuRepo.findByBooth_BoothIdAndMenuItemId(boothId, menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("MENU_NOT_FOUND"));
        return toDto(m);
    }

    @Transactional(readOnly = true)
    public List<MenuItemDto> list(Long boothId) {
        return menuRepo.findByBooth_BoothIdOrderByNameAsc(boothId)
                .stream().map(this::toDto).toList();
    }

    public MenuItemDto create(CreateMenuRequest req) {
        Booth booth = boothRepo.findById(req.boothId())
                .orElseThrow(() -> new IllegalArgumentException("BOOTH_NOT_FOUND"));

        if (menuRepo.existsByBooth_BoothIdAndName(req.boothId(), req.name()))
            throw new IllegalArgumentException("DUPLICATE_MENU_NAME");

        MenuItem m = MenuItem.builder()
                .booth(booth)
                .name(req.name())
                .price(req.price())
                .available(req.available() == null ? true : req.available())
                .modelUrl(req.modelUrl())
                .previewImage(req.previewImage())
                .description(req.description())
                .category(Category.valueOf(req.category().toUpperCase())) // ★
                .build();

        return toDto(menuRepo.save(m));
    }

    public MenuItemDto update(Long menuItemId, UpdateMenuRequest req) {
        MenuItem m = menuRepo.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("MENU_NOT_FOUND"));

        if (req.name() != null && !req.name().isBlank()) m.setName(req.name());
        if (req.price() != null) m.setPrice(req.price());
        if (req.available() != null) m.setAvailable(req.available());
        if (req.modelUrl() != null) m.setModelUrl(req.modelUrl());
        if (req.previewImage() != null) m.setPreviewImage(req.previewImage());
        if (req.description() != null) m.setDescription(req.description());
        if (req.category() != null && !req.category().isBlank())
            m.setCategory(Category.valueOf(req.category().toUpperCase()));

        return toDto(m);
    }

    public boolean toggleAvailable(Long menuItemId) {
        MenuItem m = menuRepo.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("MENU_NOT_FOUND"));
        boolean next = !Boolean.TRUE.equals(m.getAvailable());
        m.setAvailable(next);
        return next;
    }

    public void delete(Long menuItemId) {
        if (!menuRepo.existsById(menuItemId)) return;
        menuRepo.deleteById(menuItemId);
    }

    private MenuItemDto toDto(MenuItem m) {
        return new MenuItemDto(
                m.getMenuItemId(),
                m.getBooth().getBoothId(),
                m.getName(),
                m.getPrice(),
                m.getAvailable(),
                m.getModelUrl(),
                m.getPreviewImage(),
                m.getDescription(),
                m.getCategory() == null ? null : m.getCategory().name()
        );
    }
}
