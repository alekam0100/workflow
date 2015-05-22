package application;

import application.domain.Reservation;
import application.validation.ReservationValidator;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.component.facebook.config.FacebookConfiguration;
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


                rest().get("/facebook").route().to("facebook://postStatusMessage?message=tolo");
                rest().post("/checkin").route().process(authProcessor).to("bean:checkinController?method=checkin(*)");
            }
        };
    }

    @Bean
    public FacebookConfiguration configuration() {
        FacebookConfiguration conf = new FacebookConfiguration();
        conf.setOAuthAppId("638956319573085");
        conf.setOAuthAppSecret("78bdf9ea07879ecf65e6538b69a13ea7");
        conf.setOAuthAccessToken("CAAFkkqPiouwBAMZA7dyCn83ROA3gTJ24ZCWTfeIW4N8Y5Mf1fN6ABAHZCA6CkIt4y5ZCzGHqYA5VekC0Hkh2j0dTHNDWn1arqIcZA4bhGc3ChfFSi0FhNoiTonUaxo2TZCzktmxzdE17UwMg09WeRWGtQrJVm8ElhEP4jnZAZAvC7XSL5WQKHZCQ9LxN8NyFlNRVZB5EAOiaCqzFH0emSSG1OF");
        return conf;
    }
}
