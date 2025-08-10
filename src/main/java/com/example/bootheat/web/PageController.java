package com.example.bootheat.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {
    @GetMapping("/t/{boothId}/{tableId}")
    public String tablePage(@PathVariable Long boothId,
                            @PathVariable Integer tableId,
                            Model model) {
        model.addAttribute("boothId", boothId);
        model.addAttribute("tableId", tableId);
        return "t"; // templates/t.html
    }

    @GetMapping("/") // 간단 홈
    public String home() { return "index"; }

    @GetMapping("/test-order")
    public String testOrderPage() {
        return "test-order";
    }
}
