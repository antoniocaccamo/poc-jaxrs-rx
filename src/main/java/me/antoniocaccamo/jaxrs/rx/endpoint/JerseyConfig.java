package me.antoniocaccamo.jaxrs.rx.endpoint;

import me.antoniocaccamo.jaxrs.rx.endpoint.rate.RateEndpoint;
import me.antoniocaccamo.jaxrs.rx.endpoint.exception.mapper.CurrencyNotFoundExceptionMapper;
import me.antoniocaccamo.jaxrs.rx.endpoint.exception.mapper.InternaleErrorExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
	
	public JerseyConfig() {

		register(RateEndpoint.class);

		register(CurrencyNotFoundExceptionMapper.class);
		register(InternaleErrorExceptionMapper.class);
		
	}

}
