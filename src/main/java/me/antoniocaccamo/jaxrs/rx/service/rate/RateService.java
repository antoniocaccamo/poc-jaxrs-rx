package me.antoniocaccamo.jaxrs.rx.service.rate;

import io.reactivex.Single;
import me.antoniocaccamo.jaxrs.rx.model.ExchangeRatesResponse;

public interface RateService {

    Single<ExchangeRatesResponse> getExchangeRates(final String base);
}
