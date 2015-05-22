package application.service;

import application.dataaccess.ReservationRepository;
import application.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * handles reservation of tables
 */
@Service
public class ReservationService {

	@Autowired
	ReservationRepository reservationRepository;

	public Reservation getReservation(int id) {
		Reservation reservation = new Reservation();
		reservation.setPkIdReservation(id);
		return reservation;
	}

	public List<Reservation> getAllReservations() {
		List<Reservation> reservations = reservationRepository.findAll();
		reservations.add(new Reservation()); // TODO
		return reservations;
	}

	public List<Reservation> getMyReservations() {
		List<Reservation> reservations = new ArrayList<>();
		Reservation reservation = new Reservation();
		reservation.setPkIdReservation(-42);
		reservations.add(reservation);
		return reservations;
	}


	public Reservation createReservation(Reservation reservation) {
//		return reservationRepository.saveAndFlush(reservation);
		return reservation;
	}

	public boolean checkIn(int tableId, int userId, Timestamp timestamp){
		Reservation reservation = reservationRepository.findByTableIdAndUserIdAndTime(tableId, userId, timestamp);
		if (reservation != null) {
			reservation.setFkIdReservationstatus(3); // status for checked-in
			reservationRepository.saveAndFlush(reservation);
			return true;
		}
		else
			return false; //checkin not successful
	}
}
