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
import application.domain.Customer;
import application.domain.Reservation;
import application.service.BillService;
import application.service.ReservationService;
import application.service.TokenManager;

@Component
public class PaymentController {
	private HashMap<String, Object> map = new HashMap<String, Object>();
	private Reservation reservation;
	private Customer cust;
	private String rid;
	
	@Autowired
	private TokenManager tokenManager;
	@Autowired
	private ReservationService resService;
	@Autowired
	private BillService billService;
		
	public void getEmailRegistered(Exchange exchange) {
		String email = tokenManager.getCurrentUser().getCustomer().getEmail();
		exchange.getOut().setHeader("to", email);
	}
	
	public void createBill(String rid, Exchange exchange) {
		this.rid = rid;
		cust = tokenManager.getCurrentUser().getCustomer();
		reservation = resService.getReservation(Integer.parseInt(rid));
		map = new HashMap<String, Object>();
		try {
			Bill bill = billService.createBill(reservation);
			if(bill == null){
				map.put("error", "No open orders found for your reservation");
				exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
				exchange.getOut().setBody(map);
			}
			else {
				exchange.getOut().setBody("Dear Mr./Mrs. " + cust.getFirstName() +",\n" + "your invoice:\n" + billService.getBillAmount(reservation));
				exchange.getOut().setHeader("email",exchange.getIn().getHeader("email"));
			}
		} catch (MissingServletRequestParameterException
				| ObjectNotFoundException | IOException e) {
			map.put("error", "Exception " + e.getClass().getCanonicalName());
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 500);
			exchange.getOut().setBody(map);
		}		
	}
	
	public void billPayed(String rid, Exchange exchange) {
		reservation = resService.getReservation(Integer.parseInt(rid));
		billService.closeBill(reservation);
		map = new HashMap<String, Object>();
		map.put("billstatus", "billstatus changed to closed");
		exchange.getOut().setBody(map);
	}
	
	public void validationException(Exchange exchange) {
		map.put("error", "The email given has wrong format.");
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
		exchange.getOut().setBody(map);
	}
}
