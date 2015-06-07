package application.service;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;

import application.domain.Order;

public class OrderFilter {
	
	@Autowired
	private TokenManager tokenManager;
	
	public boolean doesReservationBelongToUser(Exchange exchange) {
		Order order = exchange.getIn().getBody(Order.class);
		//if(!order.getReservation().getCustomer().getFkIdUser().equals(tokenManager.getCurrentUser().getPkIdUser())) {
		//	return false;
		//}
		return true;
	}
}
