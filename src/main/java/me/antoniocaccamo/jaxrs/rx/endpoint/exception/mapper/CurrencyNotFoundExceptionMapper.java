package me.antoniocaccamo.jaxrs.rx.endpoint.exception.mapper;

import me.antoniocaccamo.jaxrs.rx.endpoint.exception.CurrencyNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author antoniocaccamo on 12/02/2020
 */
public class CurrencyNotFoundExceptionMapper implements ExceptionMapper<CurrencyNotFoundException> {
    @Override
    public Response toResponse(CurrencyNotFoundException e) {
        return Response.status(
                Response.Status.NOT_FOUND.getStatusCode(),
                e.getMessage()
        ).build();
    }
}
