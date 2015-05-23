package application;

import application.domain.Reservation;
import application.validation.ReservationValidator;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.facebook.config.FacebookConfiguration;
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


                rest().post("/facebook").route().process(authProcessor).to("bean:checkinController?method=facebook(*)")
                        .to("facebook://postStatusMessage?message=I%20just%20checked%20into%20restaurant");

                rest().post("/twitter").route().process(authProcessor).to("bean:checkinController?method=twitter(*)")
                        .setBody(constant("I just checked into restaurant."))
                        .to("twitter://timeline/user?" +
                                "consumerKey=0jUNvvJfB6vTxuGsLxAUfnft9" +
                                "&consumerSecret=FA5lOeaxCjks2AmUNyWWreVM9Zf7CiUSqodOfYPPYxdVlckD0t" +
                                "&accessToken=906126062-U2EnxiQKTVPZV4D3v0LeE6B1yKhM0CCVGBjWgxE6" +
                                "&accessTokenSecret=3KOWxtDJtal0tiuKRShTyz5XpJdPntBDcUewsstp2cUyL");

                rest().post("/checkin").route().process(authProcessor).to("bean:checkinController?method=checkin(*)");
                rest().get("/food").route().process(authProcessor).to("bean:foodController?method=food(*)");
            }
        };
    }

    @Bean
    public FacebookConfiguration configuration() {
        FacebookConfiguration conf = new FacebookConfiguration();
        conf.setOAuthAppId("638956319573085");
        conf.setOAuthAppSecret("78bdf9ea07879ecf65e6538b69a13ea7");
        conf.setOAuthAccessToken("CAAJFIJx0hF0BANzm2BZCAZBineXS8dCRORZBlKmEgZC6MGq72OGOHKEAUZBkLvaTGlcpr6iHHKKSxWi3eUsINw2pKsClprbk8LVzrdruJS4wHxO8R8VNTTewFj2M3ySYKDuogWPWzk4ApLcEZCyiea4hPJlxGTkhrnkvgvI9wKZCjUZA7Gn9XWHUAxJZBjB9AVyg9W1FZCHUFQky9e8MzYmLvz");
        return conf;
    }

    // twitter consumer key = 0jUNvvJfB6vTxuGsLxAUfnft9
    // twitter consumer secret = FA5lOeaxCjks2AmUNyWWreVM9Zf7CiUSqodOfYPPYxdVlckD0t
    // test access token = 906126062-U2EnxiQKTVPZV4D3v0LeE6B1yKhM0CCVGBjWgxE6
    // test access token secret = 3KOWxtDJtal0tiuKRShTyz5XpJdPntBDcUewsstp2cUyL
}
