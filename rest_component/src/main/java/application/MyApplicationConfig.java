package application;

import application.domain.Customer;
import application.domain.Reservation;
import application.exceptions.AuthenticationException;
import application.exceptions.CheckinException;
import application.service.LoginFilter;
import application.service.OrderFilter;
import javassist.NotFoundException;
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
    @Autowired
    private HeaderProcessor headerProcessor;


    @Bean
    public RouteBuilder route() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                restConfiguration().component("restlet").host("localhost").port(8080).bindingMode(RestBindingMode.auto);
                onException(NotFoundException.class).handled(true).to("bean:loginController?method=handleError(*)").marshal().json(JsonLibrary.Jackson);
                onException(AuthenticationException.class).handled(true).to("bean:authenticationProcessor?method=handleError(*)").marshal().json(JsonLibrary.Jackson);
                onException(CheckinException.class).handled(true).to("bean:checkinController?method=handleError(*)").marshal().json(JsonLibrary.Jackson);

                
                rest().get("/greeting").route().process(authProcessor).to("bean:greetingController?method=greeting");
                rest().post("/login").route().filter().method(LoginFilter.class,"areHeadersAvailable").to("bean:loginController?method=login(*)").end().to("bean:loginController?method=evaluateResult(*)").marshal().json(JsonLibrary.Jackson);


                rest().post("/reservation").consumes("application/json").type(Reservation.class)
                        .route().process(authProcessor).to("bean-validator:res") // auth & validate
                        .marshal().json(JsonLibrary.Jackson).wireTap("file://reservations").end()
                        .unmarshal().json(JsonLibrary.Jackson, Reservation.class).to("bean:reservationController?method=createReservation(*)")
                ;

                rest().get("/reservation").route().to("bean:reservationController?method=getAllReservations(*)");
                rest().get("/reservation/my").route().to("bean:reservationController?method=getMyReservations(*)");
                rest().get("/reservation/{id}").route().to("bean:reservationController?method=getReservation(${header.id},*)");
                rest("/reservation/{id}").post("orders").route().process(authProcessor).filter().method(OrderFilter.class, "doesReservationBelongToUser(*,${header.id})").to("bean-validator:ordVal").to("bean:orderController?method=saveOrder(*)").end().to("bean:orderController?method=evaluateResult(*)").marshal().json(JsonLibrary.Jackson);
                
            /*    rest().post("/register").consumes("application/json").type(Customer.class)
                .route().to("bean-validator:res") // auth & validate
                .marshal().json(JsonLibrary.Jackson).wireTap("file://register").end()
                .unmarshal().json(JsonLibrary.Jackson, Customer.class).to("bean:customerController?method=addCustomer(*)")
        ; */
                rest().post("/register").type(Customer.class).route().to("bean:customerController?method=addCustomer(*)");
                rest().get("/register").route().to("bean:customerController?method=getCustomer(*)");
                rest().get("/register/my").route().to("bean:customerController?method=getMyCustomer(*)");

                /* implement content-based router -> add header parameter "method" in your payload in postman and here
                 * make .choice().when(header("method").isEqualTo("facebook")).to(beanblabla?method=facebook(*)
                 * link: http://camel.apache.org/content-based-router.html
                 */

                rest().post("/tables/{id}/checkin").route().process(authProcessor)
                        .to("bean:checkinController?method=checkin(${header.id}, *)")
                        .choice()
                            .when(header("social").isEqualTo("facebook"))
                            .to("facebook://postStatusMessage?message=I%20just%20checked%20into%20restaurant.")
                        .when(header("social").isEqualTo("twitter"))
                            .setBody(header("message"))
                            .to("twitter://timeline/user?" +
                                    "consumerKey=0jUNvvJfB6vTxuGsLxAUfnft9" +
                                    "&consumerSecret=FA5lOeaxCjks2AmUNyWWreVM9Zf7CiUSqodOfYPPYxdVlckD0t" +
                                    "&accessToken=906126062-U2EnxiQKTVPZV4D3v0LeE6B1yKhM0CCVGBjWgxE6" +
                                    "&accessTokenSecret=3KOWxtDJtal0tiuKRShTyz5XpJdPntBDcUewsstp2cUyL");


                rest().get("/food").route().process(authProcessor).to("bean:foodController?method=food(*)");
                
                /*
                 * Payment route
                 * used patterns: content-based filter, validate
                 */
                // exception handling for email validataion
//                onException(ValidationException.class).handled(true).to("bean:paymentController?method=validationException(*)")
//        		.marshal().json(JsonLibrary.Jackson);
                
                rest().post("/reservation/{rid}/payment").route().process(authProcessor).process(headerProcessor)
                .to("bean:paymentController?method=initPayment(${header.rid},*)")
                .choice()
                	.when(header("email").isEqualTo("1"))
                		.to("bean:paymentController?method=sendEmailRegistered(*)")
                	.when(header("email").isNotNull()).validate(header("email").regex("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$"))
                		.to("bean:paymentController?method=sendEmail(*)").endChoice()
                .to("bean:paymentController?method=createBill(*)");
                	
                //Simple email expression. Doesn't allow numbers in the domain name and doesn't allow for top level domains 
                //that are less than 2 or more than 3 letters (which is fine until they allow more).
                
                
                
            }
        };
    }

    //    @Bean
//    public HibernateValidationProviderResolver myValidationProviderResolver(){
//        return new org.apache.camel.component.bean.validator.HibernateValidationProviderResolver();
//    }
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
