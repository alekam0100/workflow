package application.controller;

import application.service.CheckinService;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.restlet.util.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class CheckinController {
    @Autowired
    private CheckinService checkinService;

    public void checkin(String id, @Header("org.restlet.http.headers") Series<?> headers, Exchange exchange) {
        if (id == null || id.isEmpty() || headers.getFirstValue("X-timestamp") == null) {
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, "400");
            exchange.getOut().setBody("No table id or timestamp found");
            return;
        }


        Integer tableId = Integer.parseInt(id);
        Timestamp timestamp = Timestamp.valueOf(headers.getFirstValue("X-timestamp"));

        try {
            checkinService.checkIn(tableId, timestamp);
        } catch (Exception e) {
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, "400");
            exchange.getOut().setBody(e.getMessage());
            return;
        }

        exchange.getOut().setHeader("social", headers.getFirstValue("Social"));
        exchange.getOut().setHeader("message", headers.getFirstValue("Message"));
    }
}
