package me.antoniocaccamo.jaxrs.rx.endpoint;

import me.antoniocaccamo.jaxrs.rx.endpoint.rate.RateEndpoint;
import me.antoniocaccamo.jaxrs.rx.service.rate.RateService;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
	
	public JerseyConfig() {

		register(RateEndpoint.class);
		
	}

}
