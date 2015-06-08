package application.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class DataIntegrityViolationExceptionHandler {
	public void handleError(Exchange exchange) {
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
		Map<String,String> map = new HashMap<String,String>();
		map.put("error", "Data integrity violation! Some input does not fulfill the constraints given in the database");
		exchange.getOut().setBody(map);
	}
}
