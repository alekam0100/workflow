package application.service;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;

import application.dataaccess.ReservationRepository;
import application.domain.Order;

public class OrderFilter {
	
	@Autowired
	private TokenManager tokenManager;
	
	@Autowired
	private ReservationRepository reservationRepo;
	
	public boolean doesReservationBelongToUser(Exchange exchange, int reservationId) {
		Order order = exchange.getIn().getBody(Order.class);
		if(!reservationRepo.exists(reservationId) || order.getReservation().getCustomer().getFkIdUser() != tokenManager.getCurrentUser().getPkIdUser()) {
			return false;
		}
		return true;
	}
}
