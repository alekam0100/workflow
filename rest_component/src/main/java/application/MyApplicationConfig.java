package application;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyApplicationConfig {
	
	@Autowired
	private CamelContext camelContext;
	
	@Autowired
	private AuthenticationProcessor authProcessor;
	
	
	@Bean
	public RouteBuilder route() {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				restConfiguration().component("restlet").host("localhost").port(8080).bindingMode(RestBindingMode.auto);
				rest().get("/greeting").route().process(authProcessor).to("bean:greetingController?method=greeting");
				rest().post("/login").route().to("bean:loginController?method=login(*)");
				rest().get("/reservation").route().to("bean:reservationController?method=getAllReservations");
				rest().post("/reservation").route().to("bean:reservationController?method=createReservation");
			}
		};
	}
	
}
