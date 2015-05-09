package application;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import application.domain.Greeting;

@Configuration
public class MyApplicationConfig {
	
	@Autowired
	private CamelContext camelContext;
	
	@Bean
	public RouteBuilder route() {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				restConfiguration().component("restlet").host("localhost").port(8080).bindingMode(RestBindingMode.auto);
				rest().get("/greeting").outType(Greeting.class).to("bean:greetingController?method=greeting");
			}
		};
	}
	
}
