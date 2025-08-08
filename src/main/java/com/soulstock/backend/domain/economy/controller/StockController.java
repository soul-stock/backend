package com.soulstock.backend.domain.economy.controller;

import com.soulstock.backend.domain.economy.dto.StockInfoDto;
import com.soulstock.backend.domain.economy.dto.StockItemDto;
import com.soulstock.backend.domain.economy.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping("/list")
    public ResponseEntity<List<StockItemDto>> getStockItems() {
        List<StockItemDto> stockItems = stockService.getStockItems(10);
        System.out.println(stockItems.toString());
        return ResponseEntity.ok(stockItems);
    }

    @GetMapping("/{stockCode}")
    public ResponseEntity<StockInfoDto> getStockInfo(@PathVariable("stockCode") String stockCode) {
        StockInfoDto stockInfoDto = stockService.getStockInfo(stockCode);
        System.out.println(stockInfoDto.toString());
        return ResponseEntity.ok(stockInfoDto);
    }
}
