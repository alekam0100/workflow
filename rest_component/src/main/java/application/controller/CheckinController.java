package application.controller;

import application.exceptions.CheckinException;
import application.service.CheckinService;
import application.util.Constants;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.restlet.util.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CheckinController {
    @Autowired
    private CheckinService checkinService;

    public void checkin(String id, @Header("org.restlet.http.headers") Series<?> headers, Exchange exchange) throws CheckinException {
        if (id == null || id.isEmpty()) {
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, "400");
            exchange.getOut().setHeader(Constants.ERROR_REASON, "Table not specified");
            return;
        }


        Integer tableId = Integer.parseInt(id);
        try {
            checkinService.checkIn(tableId);
        } catch (CheckinException e) {
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
            exchange.getOut().setHeader(Constants.ERROR_REASON, e.getValue());
            throw e;
        }

        exchange.getOut().setHeader("social", headers.getFirstValue("Social"));
        exchange.getOut().setHeader("message", headers.getFirstValue("Message"));
    }

    public void handleError(Exchange exchange) {
        Map<String, String> responseMap = new HashMap<>();
        String errorMsg = (String)exchange.getOut().getHeader(Constants.ERROR_REASON);

        responseMap.put(Constants.ERROR_REASON, errorMsg);
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
        exchange.getOut().setBody(responseMap);
    }
}
