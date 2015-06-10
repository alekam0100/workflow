package application.controller;

import application.domain.Reservation;
import application.exceptions.ConstraintsViolationException;
import application.exceptions.ReservationException;
import application.service.ReservationService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	public void getAllReservations(Exchange exchange) {
		String params = exchange.getIn().getHeader("CamelHttpQuery") != null ? exchange.getIn().getHeader("CamelHttpQuery").toString() : "";
		List<Reservation> output = reservationService.getAllReservations(params.contains("onlyValid=true"), params.contains("onlyCurrentAndFuture=true"));
		exchange.getOut().setBody(output);
	}

	public void getMyReservations(Exchange exchange) {
		System.out.println("ReservationController.getMyReservations");
		String params = exchange.getIn().getHeader("CamelHttpQuery") != null ? exchange.getIn().getHeader("CamelHttpQuery").toString() : "";
		List<Reservation> output = reservationService.getMyReservations(params.contains("onlyCurrentAndFuture=true"));
		exchange.getOut().setBody(output);
	}

	public void getReservation(String id, Exchange exchange) {
		try {
			Reservation reservation = reservationService.getReservation(Integer.parseInt(id));
			if (reservation == null) {
				throw new ReservationException();
			} else {
				exchange.getOut().setBody(reservation);
			}
		} catch (NumberFormatException | ReservationException e) {
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
			Map<String, String> map = new HashMap<String, String>();
			map.put("error", "reservation not found");
			exchange.getOut().setBody(map);
		}
	}

	public void createReservation(Exchange exchange) {
		Reservation reservation = exchange.getIn().getBody(Reservation.class);
		try {
			reservation = reservationService.createReservation(reservation);
			exchange.getOut().setBody(reservation);
		} catch (ReservationException e) {
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
			Map<String, String> map = new HashMap<String, String>();
			map.put("error", "This table is not available at the defined time");
			exchange.getOut().setBody(map);
		} catch (MissingServletRequestParameterException e) {
			e.printStackTrace();
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		} catch (ConstraintsViolationException e) {
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
			Map<String, String> map = new HashMap<String, String>();
			map.put("error", "This table is not available at the defined time");
			exchange.getOut().setBody(map);
		}
	}

}
