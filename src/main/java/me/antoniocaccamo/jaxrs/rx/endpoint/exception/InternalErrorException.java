package me.antoniocaccamo.jaxrs.rx.endpoint.exception;

public class InternalErrorException extends Exception {

    public InternalErrorException() {

        super("Internal error");
    }
}