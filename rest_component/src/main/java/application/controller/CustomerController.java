package application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestBody;

import application.domain.Customer;
import application.service.CustomerService;

@Component
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	public Customer addCustomer(@RequestBody Customer c) throws MissingServletRequestParameterException {
		System.out.println("CustomerController.addCustomer");
		System.out.println("c = [" + c + "]");

		
		return c;
		
	}
	
	public Customer getMyCustomer() {
		return customerService.getMyCustomer();
	}
	
}
