package application;

import application.domain.Customer;
import application.domain.Order;
import application.domain.Reservation;
import application.exceptions.AuthenticationException;
import application.exceptions.CheckinException;
import application.service.LoginFilter;
import application.service.OrderFilter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import javassist.NotFoundException;
import org.apache.camel.CamelContext;
import org.apache.camel.ValidationException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.facebook.config.FacebookConfiguration;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityNotFoundException;

@Configuration
public class MyApplicationConfig {

    @Autowired
    private CamelContext camelContext;

    @Autowired
    private AuthenticationProcessor authProcessor;
    @Autowired
    private HeaderProcessor headerProcessor;
    @Autowired
    private ContentEnrichProcessor contentEnrichProcessor;


    @Bean
    public RouteBuilder route() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                restConfiguration().component("restlet").host("localhost").port(8080).bindingMode(RestBindingMode.auto);
                onException(NotFoundException.class).handled(true).to("bean:loginController?method=handleError(*)").marshal().json(JsonLibrary.Jackson);
                onException(AuthenticationException.class).handled(true).to("bean:authenticationProcessor?method=handleError(*)").marshal().json(JsonLibrary.Jackson);
                onException(CheckinException.class).handled(true).to("bean:checkinController?method=handleError(*)").marshal().json(JsonLibrary.Jackson);
                onException(JsonMappingException.class).handled(true).to("bean:jsonMappingExceptionHandler?method=handleError(*)").marshal().json(JsonLibrary.Jackson);
                onException(JsonParseException.class).handled(true).to("bean:jsonMappingExceptionHandler?method=handleError(*)").marshal().json(JsonLibrary.Jackson);
                onException(DataIntegrityViolationException.class).handled(true).to("bean:dataIntegrityViolationExceptionHandler?method=handleError(*)").marshal().json(JsonLibrary.Jackson);

                //onException(IllegalArgumentException.class) .. //todo to avoid empty post request


                rest().get("/greeting").route().process(authProcessor).to("bean:greetingController?method=greeting");
                rest().post("/login").route().throttle(10).timePeriodMillis(1000).filter().method(LoginFilter.class,"areHeadersAvailable").to("bean:loginController?method=login(*)").end().to("bean:loginController?method=evaluateResult(*)").marshal().json(JsonLibrary.Jackson);


                rest().post("/reservations").consumes("application/json").type(Reservation.class)
                        .route().process(authProcessor).to("bean-validator:res") // auth & validate
                        .onException(ValidationException.class).handled(true).to("bean:validationExceptionHandler?method=handleError(*)").end()
                        .marshal().json(JsonLibrary.Jackson).wireTap("file://reservations").end()
                        .unmarshal().json(JsonLibrary.Jackson, Reservation.class).to("bean:reservationController?method=createReservation(*)")

                ;

                rest().get("/reservations").route().process(authProcessor).to("bean:reservationController?method=getAllReservations(*)");
                rest().get("/reservations/my").route().process(authProcessor).to("bean:reservationController?method=getMyReservations(*)");
                rest().get("/reservations/{id}").route().process(authProcessor).to("bean:reservationController?method=getReservation(${header.id},*)");
                
                
                rest("/reservations/{id}")
                	.post("orders").type(Order.class).route().process(authProcessor)
	                	.onException(ValidationException.class).handled(true).to("bean:orderController?method=validationException(*)").marshal().json(JsonLibrary.Jackson).end()
	                	.onException(EntityNotFoundException.class).handled(true).to("bean:orderController?method=notFoundException(*)").marshal().json(JsonLibrary.Jackson).end()
	                	.to("bean-validator:order").process(contentEnrichProcessor).filter().method(OrderFilter.class, "doesReservationBelongToUser(*,${header.id})").to("bean:orderController?method=saveOrder(*)").end().to("bean:orderController?method=evaluateResult(*)");
                rest("/reservations/{id}")
                	.get("orders").type(Order.class).route().process(authProcessor)
                		.onException(EntityNotFoundException.class).handled(true).to("bean:orderController?method=notFoundException(*)").marshal().json(JsonLibrary.Jackson).end()
                		.filter().method(OrderFilter.class, "doesReservationBelongToUser2(*,${header.id})").to("bean:orderController?method=getOrders(*)").end().to("bean:orderController?method=evaluateResult(*)");

                rest().post("/customers").type(Customer.class).route().throttle(10).timePeriodMillis(1000).to("bean:customerController?method=addCustomer(*)");
                rest().get("/customers").route().process(authProcessor).to("bean:customerController?method=getCustomer(*)");
                rest().get("/customers/my").route().process(authProcessor).to("bean:customerController?method=getMyCustomer(*)");

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


                rest().get("/foods").route().process(authProcessor).to("bean:foodController?method=food(*)");
                
                /*
                 * Payment route
                 * used patterns: content-based filter, validate, message translator (headerProcessor), content filter (filterEmailProcessor);
                 */
                // exception handling for email validataion
                onException(ValidationException.class).handled(true).to("bean:validationExceptionHandler?method=handleError(*)")
        		.marshal().json(JsonLibrary.Jackson);
                
                rest().post("/reservations/{rid}/payment").route().process(authProcessor).process(headerProcessor)
                .to("bean:paymentController?method=createBill(${header.rid},*)")
                .choice()
                	.when(header("email").isEqualTo("1"))
                		.to("bean:paymentController?method=getEmailRegistered(*)")
                		.setHeader("subject", constant("Your bill"))
                		.to("smtps://smtp.gmail.com?username=wmpm.group09@gmail.com&password=wmpmgroup09")
                	.when(header("email").isNotNull()).validate(header("email").regex("^\\w+[\\w-\\.]*\\@\\w+((-\\w+)|(\\w*))\\.[a-z]{2,3}$"))
                		.setHeader("to", header("email"))
                		.setHeader("subject", constant("Your bill"))
                		.to("smtps://smtp.gmail.com?username=wmpm.group09@gmail.com&password=wmpmgroup09").endChoice();
                
                rest().post("/reservations/{rid}/payed").route().process(authProcessor).to("bean:paymentController?method=billPayed(${header.rid},*)");
                
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
