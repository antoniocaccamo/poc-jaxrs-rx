package me.antoniocaccamo.jaxrs.rx.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RateResponse {

    private BigDecimal rate;
}
