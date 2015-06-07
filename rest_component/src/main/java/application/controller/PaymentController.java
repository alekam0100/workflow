package application.controller;

import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import application.service.TokenManager;

@Component
public class PaymentController {
	private HashMap<String, String> map = new HashMap<String, String>();
	
	@Autowired
	private TokenManager tokenManager;
	
	public void initPayment(Exchange exchange) throws Exception {
		
	}
	
	public void sendEmailRegistered(Exchange exchange) {
		String email = tokenManager.getCurrentUser().getCustomer().getEmail();
		exchange.getOut().setBody("send email to registered adress: " + email);
	}
	
	public void sendEmail(Exchange exchange, @Header("email") String email) {
		exchange.getOut().setBody("send email to: "+ email);
	}
	
	public void test(Exchange exchange) {
		exchange.getOut().setBody("this is a test");
	}
	
	public void validationException(Exchange exchange) {
		map.put("error", "The email given has wrong format.");
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
		exchange.getOut().setBody(map);
	}
}
