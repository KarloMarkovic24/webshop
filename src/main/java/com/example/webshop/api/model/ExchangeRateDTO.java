package com.example.webshop.api.model;

import com.example.webshop.services.CroatianBigDecimalDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateDTO {

    @JsonDeserialize(using = CroatianBigDecimalDeserializer.class, as = BigDecimal.class)
    @JsonProperty("Srednji za devize")
    private BigDecimal rate;

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

}
