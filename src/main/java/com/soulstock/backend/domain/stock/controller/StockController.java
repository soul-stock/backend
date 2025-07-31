package com.soulstock.backend.domain.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class StockController {

    @GetMapping("/stock/{code}")
    public String stock(
            @PathVariable String code,
            Model model
    ) {
        model.addAttribute("stockName", "삼성전자");
        model.addAttribute("stockCode", code);
        model.addAttribute("date", "2025-07-31");
        model.addAttribute("closePrice", 75500);
        model.addAttribute("changeRate", "+1.25%");
        model.addAttribute("volume", "10,234,000");
        model.addAttribute("aiSummary", "단기적으로 상승 가능성이 있습니다.");
        model.addAttribute("labels", List.of("2025-07-25", "2025-07-26", "2025-07-27", "2025-07-28", "2025-07-29", "2025-07-30", "2025-07-31"));
        model.addAttribute("prices", List.of(73000, 74000, 73500, 74500, 75000, 74800, 75500));
        return "stock";
    }
}
