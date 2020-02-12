package me.antoniocaccamo.jaxrs.rx.adapter;

import io.reactivex.Single;
import me.antoniocaccamo.jaxrs.rx.model.ExchangeRatesResponse;

import java.time.LocalDate;

/**
 * @author antoniocaccamo on 12/02/2020
 */
public interface RateServiceAdapter {
    Single<ExchangeRatesResponse> getExchangeRates(String base);

    Single<ExchangeRatesResponse> getExchangeRates(String base, LocalDate localDate);
}
