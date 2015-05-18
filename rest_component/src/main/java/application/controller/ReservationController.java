package application.controller;

import application.MyApplicationConfig;
import application.domain.Reservation;
import application.service.ReservationService;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

	public Reservation getReservation(int id){
		System.out.println("ReservationController.getReservation");
		System.out.println("id = [" + id + "]");
		Reservation reservation = new Reservation();
		reservation.setPkIdReservation(id);
		return reservation;
	}

	public List<Reservation> getMyReservations(){
		List<Reservation> reservations = new ArrayList<>();
		Reservation reservation = new Reservation();
		reservation.setPkIdReservation(-42);
		reservations.add(reservation);
		return reservations;
	}
	public Reservation createReservation(Exchange exchange) {
		//System.out.println(exchange.getIn().getBody(Reservation.class));
		return reservationService.createReservation(exchange.getIn().getBody(Reservation.class));
	}
}
