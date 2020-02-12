package me.antoniocaccamo.jaxrs.rx.service.rate;

import io.reactivex.Single;
import me.antoniocaccamo.jaxrs.rx.adapter.RateServiceAdapter;
import me.antoniocaccamo.jaxrs.rx.adapter.RateServiceAdapterImpl;
import me.antoniocaccamo.jaxrs.rx.model.ExchangeRatesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class RateServiceImpl implements RateService {


    private final RateServiceAdapter rateServiceAdapter;

    @Autowired
    public RateServiceImpl(RateServiceAdapter rateServiceAdapter) {
        this.rateServiceAdapter = rateServiceAdapter;
    }


    @Override
    public Single<ExchangeRatesResponse> getExchangeRates(String base) {
        return rateServiceAdapter.getExchangeRates(base);
    }
}
