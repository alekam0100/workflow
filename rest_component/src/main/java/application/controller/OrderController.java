package application.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import application.domain.Order;
import application.service.OrderService;

@Component
@Transactional
public class OrderController {
	
	@Autowired
	private OrderService orderService; 
	
	public void evaluateResult(Exchange exchange) {
		boolean filterMatched = (boolean)exchange.getProperty(Exchange.FILTER_MATCHED);
		if(!filterMatched) {
			putReservationNotFoundException(exchange);
		} else if(((String)exchange.getIn().getHeader(Exchange.HTTP_METHOD)).equalsIgnoreCase("POST")){
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
			exchange.getOut().setBody(exchange.getIn().getBody(Order.class));
			exchange.getOut().setHeader(Exchange.HTTP_CHARACTER_ENCODING, "UTF-8");
		} else if(((String)exchange.getIn().getHeader(Exchange.HTTP_METHOD)).equalsIgnoreCase("GET")){
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
			exchange.getOut().setBody(exchange.getIn().getBody(List.class));
		}
	}
	
	
	public void validationException(Exchange exchange) {
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
		Map<String,String> map = new HashMap<String,String>();
		map.put("error", "Validation of input failed.");
		exchange.getOut().setBody(map);
	}
	
	public void notFoundException(Exchange exchange) {
		putReservationNotFoundException(exchange);
	}
	
	public void saveOrder(Exchange exchange) {
		Order order = exchange.getIn().getBody(Order.class);
		exchange.getIn().setBody(orderService.saveOrder(order));
	}
	
	public void getOrders(Exchange exchange) {
		int reservationId = Integer.parseInt((String)exchange.getIn().getHeader("id"));
		exchange.getIn().setBody(orderService.getOrders(reservationId));
	}
	
	private void putReservationNotFoundException(Exchange exchange) {
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
		exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "application/json");
		Character quote = new Character('"');
		String s = "{" + quote + "error" + quote + ":" + quote + "Reservation not found." + quote + "}";
		exchange.getOut().setBody(s);
	}
}
