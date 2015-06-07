package application.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class OrderController {
	
	public void evaluateResult(Exchange exchange) {
		boolean filterMatched = (boolean)exchange.getProperty(Exchange.FILTER_MATCHED);
		if(!filterMatched) {
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
			Map<String,String> map = new HashMap<String,String>();
			map.put("error", "Reservation not found");
			exchange.getOut().setBody(map);
		}
	}
}
