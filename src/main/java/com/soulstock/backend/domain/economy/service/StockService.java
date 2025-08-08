package com.soulstock.backend.domain.economy.service;

import com.soulstock.backend.domain.economy.dto.JsonResponseDto;
import com.soulstock.backend.domain.economy.dto.StockInfoDto;
import com.soulstock.backend.domain.economy.dto.StockItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {
    private final WebClient webClient;

    @Value("${fss.api.base-url}")
    private String baseUrl;

    @Value("${fss.api.key}")
    private String apiKey;

    private List<StockItemDto> RequestStockItems(String uri) {
        JsonResponseDto dto = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(JsonResponseDto.class)
                .block();
        if (dto == null) {
            throw new IllegalArgumentException("Not found data");
        }
        return dto.getResponse().getBody().getItems().getItem();
    }

    private String todayDate() {
        LocalDate today = LocalDate.now();
        return today.format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    public List<StockItemDto> getStockItems(int numOfRows) {
        String uri = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("serviceKey", apiKey)
                .queryParam("numOfRows", numOfRows)
                .queryParam("pageNo", 1)
                .queryParam("resultType", "json")
                .build()
                .toUriString();
        try {
            return RequestStockItems(uri);

        } catch (Exception e) {
            log.error("주식 정보 요청 중 Error 발생", e);
            return Collections.emptyList();
        }
    }

    public StockInfoDto getStockInfo(String stockCode) {
        String uri = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("serviceKey", apiKey)
                .queryParam("resultType", "json")
                .queryParam("numOfRows", 245)
                .queryParam("likeSrtnCd", stockCode)
                .queryParam("endBasDt", todayDate())
                .build()
                .toUriString();
        try {
            List<StockItemDto> items = RequestStockItems(uri);

            List<String> dateList = new ArrayList<>();
            List<Integer> priceList = new ArrayList<>();
            List<Integer> volumeList = new ArrayList<>();

            for (StockItemDto item : items) {
                dateList.add(item.getDate());
                priceList.add(item.getClosingPrice());
                volumeList.add(item.getTradeVolume());
            }
            return StockInfoDto.builder()
                    .stockCode(stockCode)
                    .dates(dateList.reversed())
                    .prices(priceList.reversed())
                    .volumes(volumeList.reversed())
                    .build();

        } catch (Exception e) {
            throw new IllegalArgumentException("Server RequestAPI Error");
        }
    }
}