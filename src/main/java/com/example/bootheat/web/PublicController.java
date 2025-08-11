package com.example.bootheat.web;

import com.example.bootheat.dto.*;
import com.example.bootheat.service.BoothQueryService;
import com.example.bootheat.service.MenuService;
import com.example.bootheat.service.OrderService;
import com.example.bootheat.service.QueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PublicController {
    private final QueryService queryService;
    private final OrderService orderService;
    private final BoothQueryService boothQueryService;
    private final MenuService menuService;

//    @GetMapping("/booths/{boothId}/tables/{tableNo}")
//    public TableInfoResponse table(@PathVariable Long boothId, @PathVariable Integer tableNo) {
//        return queryService.getTableInfo(boothId, tableNo);
//    }

    @GetMapping("/dev/table-context")
    public TableContextResponse tableContext(@RequestParam Long boothId,
                                             @RequestParam Integer tableNo) {
        return queryService.getTableContext(boothId, tableNo);
    }


}
