package me.antoniocaccamo.jaxrs.rx.adapter;

import com.fasterxml.jackson.databind.json.JsonMapper;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.jaxrs.rx.exception.InternalErrorException;
import me.antoniocaccamo.jaxrs.rx.model.ExchangeRatesResponse;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

@Component @Slf4j
public class RateServiceAdapter {

    private static final String EXCHANGE_RATE_BASE_END_POINT = "http://api.fixer.io/latest?base=%s";

    private JsonMapper jsonMapper = new JsonMapper();


    /**
     *
     * @param base
     * @return
     */
    public Single<ExchangeRatesResponse> getExchangeRates(String base) {
        return Single.create(new SingleOnSubscribe<ExchangeRatesResponse>() {

            public void subscribe(SingleEmitter<ExchangeRatesResponse> subscriber) {

                try {
                    String endPoint = String.format(EXCHANGE_RATE_BASE_END_POINT, base);
                    URL obj = new URL(endPoint);

                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    ExchangeRatesResponse response = readRatesFromResponse(in);
                    subscriber.onSuccess(response);

                } catch (Exception e) {
                    log.error("error occurred", e);

                    subscriber.onError(new InternalErrorException());
                }
            }

        });
    }

    private ExchangeRatesResponse readRatesFromResponse(BufferedReader in) throws Exception {

        try {
            String res = in.lines()
                    .collect(Collectors.joining());
            return jsonMapper.readValue(res, ExchangeRatesResponse.class);
        } catch (Exception e) {
            log.error("error occurred", e);
            throw new InternalErrorException();
        } finally {
            in.close();
        }
    }
}
