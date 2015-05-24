package application.controller;

import application.domain.Reservation;
import application.service.ReservationService;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	public List<Reservation> getAllReservations(Exchange exchange) {
		System.out.println("ReservationController.getAllReservations");
		String params = "";
		try {
			params = exchange.getIn().getHeader("CamelHttpQuery").toString();
		} catch (Exception e) {

		}
		return reservationService.getAllReservations(params.contains("onlyValid=true"), params.contains("onlyCurrentAndFuture=true"));
	}

	public List<Reservation> getMyReservations(Exchange exchange) {
		String params = "";
		try {
			params = exchange.getIn().getHeader("CamelHttpQuery").toString();
		} catch (Exception e) {

		}

		return reservationService.getMyReservations(params.contains("onlyCurrentAndFuture=true"));
	}

	public Reservation getReservation(String id, Exchange exchange) {
		return reservationService.getReservation(Integer.parseInt(id));
	}


	public Reservation createReservation(Exchange exchange) {
		System.out.println("ReservationController.createReservation");
		System.out.println(exchange.getIn().getBody(Reservation.class));
		return reservationService.createReservation(exchange.getIn().getBody(Reservation.class));
	}
}
