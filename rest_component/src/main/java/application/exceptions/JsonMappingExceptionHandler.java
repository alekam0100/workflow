package application.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class JsonMappingExceptionHandler {

	public void handleError(Exchange exchange) {
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
		Map<String,String> map = new HashMap<String,String>();
		map.put("error", "Invalid request payload format.");
		exchange.getOut().setBody(map);
	}
}
