package application.controller;

import application.domain.CheckinNotPossible;
import application.domain.DataValidationFailed;
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

    public void checkin(String id, @Header("org.restlet.http.headers") Series<?> headers, Exchange exchange) throws CheckinNotPossible,
            DataValidationFailed{
        if (id == null || id.isEmpty() || headers.getFirstValue("X-timestamp") == null) {
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, "404");
            exchange.getOut().setBody("No table id or timestamp");
            return;
        }


        Integer tableId = Integer.parseInt(id);
        Timestamp timestamp = Timestamp.valueOf(headers.getFirstValue("X-timestamp"));

        if (!checkinService.checkIn(tableId, timestamp)) {
                throw new CheckinNotPossible();
            }

        exchange.getOut().setHeader("social", headers.getFirstValue("Social"));
        exchange.getOut().setHeader("message", headers.getFirstValue("Message"));
    }
}
