package application.exceptions;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ValidationExceptionHandler {
	public void handleError(Exchange exchange) {
		Throwable caused = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
		Map<String, String> map = new HashMap<String, String>();
		//map.put("error", "validation error!");
		map.put("error", caused.getMessage());
		exchange.getOut().setBody(map);
	}
}
