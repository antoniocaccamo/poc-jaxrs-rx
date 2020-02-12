package me.antoniocaccamo.jaxrs.rx.endpoint.exception;

public class CurrencyNotFoundException extends Exception {

    public CurrencyNotFoundException() {

        super("Currency not supported");
    }

}
