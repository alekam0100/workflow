package application.controller;

import org.apache.camel.Header;
import org.restlet.util.Series;
import org.springframework.stereotype.Component;

import application.domain.Bill;

@Component
public class PaymentController {
	
	public Bill initPayment(@Header("org.restlet.http.headers") Series<?> headers) throws Exception {
		return new Bill();
	}
}
