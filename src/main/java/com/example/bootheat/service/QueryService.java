package com.example.bootheat.service;

import com.example.bootheat.domain.BoothTable;
import com.example.bootheat.domain.TableVisit;
import com.example.bootheat.dto.TableContextResponse;
import com.example.bootheat.dto.TableInfoResponse;
import com.example.bootheat.repository.BoothTableRepository;
import com.example.bootheat.repository.CustomerOrderRepository;
import com.example.bootheat.repository.MenuItemRepository;
import com.example.bootheat.repository.TableVisitRepository;
import com.example.bootheat.support.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryService {
    private final BoothTableRepository tableRepo;
    private final MenuItemRepository menuRepo;
    private final TableVisitRepository visitRepo;
    private final CustomerOrderRepository orderRepo;

    public TableInfoResponse getTableInfo(Long boothId, Integer tableNo) {
        var table = tableRepo.findByBooth_BoothIdAndTableNumber(boothId, tableNo)
                .orElseThrow(() -> new IllegalArgumentException("TABLE_NOT_FOUND"));
        var menus = menuRepo.findByBooth_BoothIdAndAvailableTrue(boothId).stream()
                .map(m -> new TableInfoResponse.Menu(
                        m.getMenuItemId(), m.getName(), m.getPrice(), m.getAvailable(),
                        m.getCategory()==null?null:m.getCategory().name()
                )).toList();
        return new TableInfoResponse(boothId, table.getTableNumber(), menus);
    }

    public TableContextResponse getTableContext(Long boothId, Integer tableNo) {
        BoothTable table = tableRepo.findByBooth_BoothIdAndTableNumber(boothId, tableNo)
                .orElseThrow(() -> new IllegalArgumentException("TABLE_NOT_FOUND"));

        // 현재 OPEN visit (없을 수 있음)
        TableVisit v = visitRepo
                .findFirstByTable_TableIdAndStatusOrderByStartedAtDesc(table.getTableId(), Status.OPEN)
                .orElse(null);

        TableContextResponse.Visit visitDto = null;
        if (v != null) {
            visitDto = new TableContextResponse.Visit(
                    v.getVisitId(),
                    v.getVisitNo(),
                    v.getStatus(),
                    v.getStartedAt().atZone(ZoneId.systemDefault()).toInstant(),
                    v.getClosedAt() == null ? null : v.getClosedAt().atZone(ZoneId.systemDefault()).toInstant()
            );
        }

        var rows = orderRepo.findTop10ByTable_TableIdOrderByCreatedAtDesc(table.getTableId())
                .stream()
                .map(o -> new TableContextResponse.OrderRow(
                        o.getOrderId(),
                        o.getOrderCode(),
                        o.getStatus(),
                        o.getTotalAmount(),
                        o.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant(),
                        o.getApprovedAt() == null ? null : o.getApprovedAt().atZone(ZoneId.systemDefault()).toInstant(),
                        o.getVisit().getVisitId()
                ))
                .toList();

        return new TableContextResponse(boothId, tableNo, visitDto, rows);
    }
}
