package application.controller;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestBody;

import application.domain.Customer;
import application.domain.Reservation;
import application.service.CustomerService;

@Component
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	public Customer addCustomer(Exchange exchange) throws MissingServletRequestParameterException {
		System.out.println("CustomerController.addCustomer");
		System.out.println(exchange.getIn().getBody(Customer.class));
		//System.out.println("c = [" + c + "]");
		return customerService.addCustomer(exchange.getIn().getBody(Customer.class));
		
		//return c;
		
	}
	
	public Customer getMyCustomer() {
		return customerService.getMyCustomer();
	}
	
	public Customer getCustomer(int userId, Exchange exchange){
		return customerService.getCustomer(userId);
	}
}
