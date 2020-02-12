package me.antoniocaccamo.jaxrs.rx.endpoint.exception.mapper;

import me.antoniocaccamo.jaxrs.rx.endpoint.exception.InternalErrorException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author antoniocaccamo on 12/02/2020
 */
public class InternaleErrorExceptionMapper implements ExceptionMapper<InternalErrorException> {
    @Override
    public Response toResponse(InternalErrorException e) {
        return Response.status(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                e.getMessage()
        ).build();
    }
}
