package application.exceptions;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class ValidationExceptionHandler {
	public void handleError(Exchange exchange) {
		Throwable caused = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
		exchange.getOut().setBody(caused.getMessage());
	}
}
