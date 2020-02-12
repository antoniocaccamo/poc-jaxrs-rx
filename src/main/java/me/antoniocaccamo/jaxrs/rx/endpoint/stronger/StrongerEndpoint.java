package me.antoniocaccamo.jaxrs.rx.endpoint.stronger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import me.antoniocaccamo.jaxrs.rx.endpoint.exception.InternalErrorException;
import me.antoniocaccamo.jaxrs.rx.model.RateResponse;
import me.antoniocaccamo.jaxrs.rx.model.StrongerResponse;
import me.antoniocaccamo.jaxrs.rx.service.stronger.StrongerService;
import org.glassfish.jersey.server.ManagedAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
@Path("/stronger")
@Slf4j
public class StrongerEndpoint {


    @Autowired
    private StrongerService strongerService;


    @GET
    @Path("{baseCurrency}/{counterCurrency}")
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void getRate(@Suspended final AsyncResponse async,
                        @PathParam("baseCurrency")    final String baseCurrency,
                        @PathParam("counterCurrency") final String counterCurrency) {

        final StrongerResponse response = new StrongerResponse();
        final CountDownLatch outerLatch = new CountDownLatch(1);

        strongerService.isStronger(baseCurrency,counterCurrency)
                .subscribe(

                        new SingleObserver<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable disposable) {}

                            @Override
                            public void onSuccess(Boolean aBoolean) {
                                response.setStronger(aBoolean);
                                outerLatch.countDown();
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                log.error("error occurred", throwable);
                                async.resume(new InternalErrorException());
                                outerLatch.countDown();
                            }
                        }
                );

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
