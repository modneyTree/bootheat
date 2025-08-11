package com.example.bootheat.dto;

import java.util.List;

public record TableInfoResponse(
        Long boothId, Integer tableNo, List<Menu> menus
){
    public record Menu(Long menuItemId, String name, Integer price, Boolean available, String category) {}
}
