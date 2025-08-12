package com.example.bootheat.web;

import com.example.bootheat.dto.OrderDetailManagerResponse;
import com.example.bootheat.dto.OrderIdsResponse;
import com.example.bootheat.dto.TableContextResponse;
import com.example.bootheat.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
class PublicQueryController {
    private final QueryService queryService;

    // GET /api/tables/{tableId}/visits/latest/orders
    @GetMapping("/tables/{tableId}/visits/latest/orders")
    public OrderIdsResponse latestVisitOrders(@PathVariable Long tableId) {
        var rows = queryService.getLatestVisitOrders(tableId); // List<OrderRow>
        var ids = rows.stream().map(r -> r.orderId()).toList();
        return new OrderIdsResponse(ids);
    }

    // GET /api/booths/{boothId}/tables/{tableId}/orders
    @GetMapping("/booths/{boothId}/tables/{tableId}/orders")
    public List<OrderDetailManagerResponse> tableOrdersPublic(@PathVariable Long boothId,
                                                              @PathVariable Long tableId) {
        return queryService.getTableOrderDetails(boothId, tableId);
    }
}