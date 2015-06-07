package application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import application.domain.Bill;
import application.service.TokenManager;

@Component
public class PaymentController {
	
	@Autowired
	private TokenManager tokenManager;
	
	public Bill initPayment() throws Exception {
		return new Bill();
	}
}
