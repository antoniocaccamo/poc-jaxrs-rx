package me.antoniocaccamo.jaxrs.rx.service.stronger;

import io.reactivex.Single;

public interface StrongerService {

    Single<Boolean> isStronger(String baseCurrency, String counterCurrency);

}
