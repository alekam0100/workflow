package application.exceptions;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ValidationExceptionHandler {
	public void handleError(Exchange exchange) {
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
		Map<String,String> map = new HashMap<String,String>();
		map.put("error", "validation error!");
		exchange.getOut().setBody(map);
	}
}
