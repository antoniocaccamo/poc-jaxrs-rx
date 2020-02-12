package me.antoniocaccamo.jaxrs.rx.endpoint.rate;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.jaxrs.rx.endpoint.exception.CurrencyNotFoundException;
import me.antoniocaccamo.jaxrs.rx.endpoint.exception.InternalErrorException;
import me.antoniocaccamo.jaxrs.rx.model.ExchangeRatesResponse;
import me.antoniocaccamo.jaxrs.rx.model.RateResponse;
import me.antoniocaccamo.jaxrs.rx.service.rate.RateService;
import org.glassfish.jersey.server.ManagedAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
@Path("/rates")
@Slf4j
public class RateEndpoint {

    @Autowired
    private RateService rateService;


    @GET @Path("{baseCurrency}/{counterCurrency}")
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void getRate(@Suspended final AsyncResponse async,
                        @PathParam("baseCurrency")    final String baseCurrency,
                        @PathParam("counterCurrency") final String counterCurrency) {

        final     RateResponse response = new RateResponse();
        final CountDownLatch outerLatch = new CountDownLatch(1);

        rateService.getExchangeRates(baseCurrency)
                .subscribe(new SingleObserver<ExchangeRatesResponse>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                    }

                    @Override
                    public void onSuccess(ExchangeRatesResponse exchangeRatesResponse) {
                        if (exchangeRatesResponse.getRates().containsKey(counterCurrency))
                            response.setRate(exchangeRatesResponse.getRates().get(counterCurrency));
                        else
                            async.resume(new CurrencyNotFoundException());
                        outerLatch.countDown();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        log.error("error occurred", throwable);
                        async.resume(new InternalErrorException());
                        outerLatch.countDown();
                    }
                });

        try {
            if (!outerLatch.await(10, TimeUnit.SECONDS)) {
                async.resume(new InternalErrorException());
            }
        } catch (Exception e) {
            async.resume(new InternalErrorException());
        }

        async.resume(response);
    }
	

}
