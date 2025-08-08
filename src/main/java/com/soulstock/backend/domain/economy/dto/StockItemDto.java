package com.soulstock.backend.domain.economy.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 나머지 필드는 무시
public class StockItemDto {

    @JsonAlias("basDt")
    private String date;

    @JsonAlias("srtnCd")
    private String stockCode;

    @JsonAlias("itmsNm")
    private String stockName;

    @JsonAlias("mrktCtg")
    private String market;

    @JsonAlias("clpr")
    private int closingPrice;

    @JsonAlias("vs")
    private int variation;

    @JsonAlias("fltRt")
    private String variationRate;

    @JsonAlias("trqu")
    private int tradeVolume;

    @JsonAlias("trPrc")
    private long tradePrice;

    @JsonAlias("lstgStCnt")
    private long stockCount;

    @JsonAlias("mrktTotAmt")
    private long totalAmount;

    private String category = "domestic";
}
