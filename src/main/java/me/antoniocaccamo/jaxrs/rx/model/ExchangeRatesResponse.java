package me.antoniocaccamo.jaxrs.rx.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data @AllArgsConstructor
public class ExchangeRatesResponse {

    private String base;
    private String date;
    private Map<String, BigDecimal> rates;
}
