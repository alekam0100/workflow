package application;

import application.domain.Reservation;
import application.validation.ReservationValidator;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
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

				rest().post("/reservation").consumes("application/json").type(Reservation.class)
						.route().process(authProcessor).bean(ReservationValidator.class) // auth & validate
						.marshal().json(JsonLibrary.Jackson).wireTap("file://reservations").end()
						.unmarshal().json(JsonLibrary.Jackson, Reservation.class).to("bean:reservationController?method=createReservation(*)")
				;

				rest().get("/reservation").route().to("bean:reservationController?method=getAllReservations(*)");
				rest().get("/reservation/my").route().to("bean:reservationController?method=getMyReservations(*)");
				rest().get("/reservation/{id}").route().to("bean:reservationController?method=getReservation(${header.id},*)");

			}
		};
	}

}
