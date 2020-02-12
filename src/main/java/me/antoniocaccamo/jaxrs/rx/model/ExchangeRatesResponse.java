package me.antoniocaccamo.jaxrs.rx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data @AllArgsConstructor @NoArgsConstructor
public class ExchangeRatesResponse {

    private String base;
    private String date;
    private Map<String, BigDecimal> rates;
}
