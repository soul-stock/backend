package com.soulstock.backend.domain.economy.service;

import com.soulstock.backend.domain.economy.dto.JsonResponseDto;
import com.soulstock.backend.domain.economy.dto.StockItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

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

    public List<StockItemDto> getStockItems() {
        String uri = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("serviceKey", apiKey)
                .queryParam("numOfRows", 3)
                .queryParam("pageNo", 1)
                .queryParam("resultType", "json")
                .build()
                .toUriString();

        try {
            JsonResponseDto responseDto = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(JsonResponseDto.class)
                    .block();

            if (responseDto != null) {
                return responseDto.getResponse().getBody().getItems().getItem();
            } else {
                log.warn("응답 정보가 Null 입니다.");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            log.error("주식 정보 요청 중 Error 발생", e);
            return Collections.emptyList();
        }
    }
}
