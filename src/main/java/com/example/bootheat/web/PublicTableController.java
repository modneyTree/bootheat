package com.example.bootheat.web;


import com.example.bootheat.dto.TableDto;
import com.example.bootheat.dto.TableListItem;
import com.example.bootheat.service.QueryService;
import com.example.bootheat.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/booths")
@RequiredArgsConstructor
class PublicTableController {
    private final TableService tableService;
    private final QueryService queryService;

    // GET /api/booths/{boothId}/tables
    @GetMapping("/{boothId}/tables")
    public List<TableListItem> list(@PathVariable Long boothId) {
        return tableService.listWithVisitStatus(boothId);
    }
}