package com.example.bootheat.service;

import com.example.bootheat.domain.BoothTable;
import com.example.bootheat.dto.CreateTableRequest;
import com.example.bootheat.dto.TableDto;
import com.example.bootheat.dto.TableListItem;
import com.example.bootheat.repository.BoothRepository;
import com.example.bootheat.repository.BoothTableRepository;
import com.example.bootheat.repository.TableVisitRepository;
import com.example.bootheat.support.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TableService {
    private final BoothRepository boothRepo;
    private final BoothTableRepository tableRepo;
    private final TableVisitRepository visitRepo;

    public TableDto create(Long boothId, CreateTableRequest req) {
        var booth = boothRepo.findById(boothId)
                .orElseThrow(() -> new IllegalArgumentException("BOOTH_NOT_FOUND"));
        if (tableRepo.existsByBooth_BoothIdAndTableNumber(boothId, req.tableNumber()))
            throw new IllegalArgumentException("DUPLICATE_TABLE_NO");
        var t = BoothTable.builder()
                .booth(booth)
                .tableNumber(req.tableNumber())
                .active(req.active()==null?true:req.active())
                .build();
        tableRepo.save(t);
        return new TableDto(t.getTableId(), boothId, t.getTableNumber(), t.getActive(), t.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public List<TableDto> list(Long boothId) {
        return tableRepo.findByBooth_BoothIdOrderByTableNumberAsc(boothId).stream()
                .map(t -> new TableDto(t.getTableId(), boothId, t.getTableNumber(), t.getActive(), t.getCreatedAt()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TableListItem> listWithVisitStatus(Long boothId) {
        var tables = tableRepo.findByBooth_BoothIdOrderByTableNumberAsc(boothId);
        return tables.stream().map(t -> {
            boolean open = visitRepo.existsByTable_TableIdAndStatus(t.getTableId(), Status.OPEN);
            return new TableListItem(
                    t.getTableId(),
                    t.getTableNumber(),
                    t.getActive(),
                    open ? "OPEN" : "CLOSED"
            );
        }).toList();
    }
}
