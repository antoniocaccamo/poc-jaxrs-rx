package me.antoniocaccamo.jaxrs.rx.service.stronger;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import me.antoniocaccamo.jaxrs.rx.adapter.RateServiceAdapter;
import me.antoniocaccamo.jaxrs.rx.endpoint.exception.CurrencyNotFoundException;
import me.antoniocaccamo.jaxrs.rx.model.ExchangeRatesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;

@Component
public class StrongerServiceImpl implements StrongerService {

    @Autowired
    private RateServiceAdapter rateServiceAdapter;



    @Override
    public Single<Boolean> isStronger(String baseCurrency, String counterCurrency) {

        return Observable.zip(
                rateServiceAdapter.getExchangeRates(baseCurrency).toObservable(),
                rateServiceAdapter.getExchangeRates(baseCurrency, LocalDate.now().minus(Duration.ofDays(1))).toObservable(),
                new BiFunction<ExchangeRatesResponse, ExchangeRatesResponse, Boolean>() {
                    @Override
                    public Boolean apply(ExchangeRatesResponse t1, ExchangeRatesResponse t2) throws Exception {
                        if ( t1.getRates().containsValue(counterCurrency) &&
                             t2.getRates().containsValue(counterCurrency)) {
                            return t1.getRates().get(counterCurrency).compareTo(
                                    t2.getRates().get(counterCurrency)
                            ) > 0;
                        }
                        throw new CurrencyNotFoundException();
                    }
                }
        ).singleOrError();
    }
}
