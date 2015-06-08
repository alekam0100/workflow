package application.service;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import application.dataaccess.ReservationRepository;
import application.domain.Order;
@Transactional
@Component
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
	
	public boolean doesReservationBelongToUser2(Exchange exchange, int reservationId) {
		if(!reservationRepo.exists(reservationId) || reservationRepo.findOne(reservationId).getCustomer().getFkIdUser() != tokenManager.getCurrentUser().getPkIdUser()) {
			return false;
		}
		return true;
	}
}
