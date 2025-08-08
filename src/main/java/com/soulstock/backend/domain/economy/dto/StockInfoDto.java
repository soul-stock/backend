package com.soulstock.backend.domain.economy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockInfoDto {

    private String stockCode;
    private List<String> dates;
    private List<Integer> prices;
    private List<Integer> volumes;

}
