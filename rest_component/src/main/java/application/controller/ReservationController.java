package application.controller;

import application.MyApplicationConfig;
import application.domain.Reservation;
import application.service.ReservationService;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * reservation service
 *
 * @see MyApplicationConfig#route()
 * --- /reservations ---
 */
@Component
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	public List<Reservation> getAllReservations() {
		return reservationService.getAllReservations();
	}

	public Reservation createReservation(Exchange exchange) {
		//System.out.println(exchange.getIn().getBody(Reservation.class));
		return reservationService.createReservation(exchange.getIn().getBody(Reservation.class));
	}
}
