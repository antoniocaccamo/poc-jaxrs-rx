package me.antoniocaccamo.jaxrs.rx.exception;

public class CurrencyNotFoundException extends Exception {

    public CurrencyNotFoundException() {

        super("Currency not supported");
    }

}
