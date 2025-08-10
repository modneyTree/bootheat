package com.example.bootheat.service;

import com.example.bootheat.domain.BoothTable;
import com.example.bootheat.dto.TableInfoResponse;
import com.example.bootheat.repository.BoothTableRepository;
import com.example.bootheat.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryService {
    private final BoothTableRepository tableRepo;
    private final MenuItemRepository menuRepo;

    public TableInfoResponse getTableInfo(Long boothId, Integer tableNo) {
        var table = tableRepo.findByBooth_BoothIdAndTableNumber(boothId, tableNo)
                .orElseThrow(() -> new IllegalArgumentException("TABLE_NOT_FOUND"));
        var menus = menuRepo.findByBooth_BoothIdAndAvailableTrue(boothId).stream()
                .map(m -> new TableInfoResponse.Menu(m.getMenuItemId(), m.getName(), m.getPrice(), m.getAvailable()))
                .toList();
        return new TableInfoResponse(boothId, table.getTableNumber(), menus);
    }
}
