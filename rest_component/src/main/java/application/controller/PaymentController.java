package application.controller;

import java.io.IOException;
import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;

import application.domain.Bill;
import application.domain.Reservation;
import application.service.BillService;
import application.service.ReservationService;
import application.service.TokenManager;

@Component
public class PaymentController {
	private HashMap<String, String> map = new HashMap<String, String>();
	private String email;
	private Reservation reservation;
	
	@Autowired
	private TokenManager tokenManager;
	@Autowired
	private ReservationService resService;
	private BillService billService;
	
	public void initPayment(String rid, Exchange exchange) throws Exception {
		reservation = resService.getReservation(Integer.parseInt(rid));
	}
	
	public void sendEmailRegistered(Exchange exchange) {
		email = tokenManager.getCurrentUser().getCustomer().getEmail();
		exchange.getOut().setBody("send email to registered adress: " + email);
	}
	
	public void sendEmail(Exchange exchange, @Header("email") String email) {
		this.email = email;
		exchange.getOut().setBody("send email to: "+ email);
	}
	
	public void createBill(Exchange exchange) {
		try {
			Bill bill = billService.createBill(reservation, email);
			map.put("bill_object",bill.toString());
			exchange.getOut().setBody(map);
		} catch (MissingServletRequestParameterException
				| ObjectNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void validationException(Exchange exchange) {
		map.put("error", "The email given has wrong format.");
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
		exchange.getOut().setBody(map);
	}
}
