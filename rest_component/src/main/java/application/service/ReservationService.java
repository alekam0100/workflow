package application.service;

import application.dataaccess.ReservationRepository;
import application.domain.Reservation;
import application.domain.Reservationstatus;
import application.domain.RestaurantTable;
import application.exceptions.ReservationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private RestaurantTableService restaurantTableService;

	@Autowired
	private TimeService timeService;

	@Autowired
	private CustomerService customerService;


	public Reservation getReservation(int id) {
		Reservation reservation = reservationRepository.findOne(id);
		if (reservation == null) {
			//throw new ReservationException("reservation not found");
		}
		return reservation;
	}

	public List<Reservation> getAllReservations(boolean onlyValid, boolean onlyCurrentAndFuture) {
		List<Reservation> toReturn;
		if (onlyValid) {
			toReturn = reservationRepository.findByReservationstatusOrderByTimeFromAsc(new Reservationstatus(Reservationstatus.RESERVATIONSTATUS_VALID));
		} else {
			toReturn = reservationRepository.findAll();
		}
		if (onlyCurrentAndFuture) {
			return filterCurrentAndFutureReservations(toReturn);
		}
		return toReturn;
	}


	private List<Reservation> filterCurrentAndFutureReservations(List<Reservation> unfiltered) {
		List<Reservation> filtered = new ArrayList<Reservation>();
		for (Reservation r : unfiltered) {
			if (r.getTimeTo().after(timeService.getCurrentTimestamp())) {
				filtered.add(r);
			}
		}
		return filtered;
	}
	public List<Reservation> getMyReservations(boolean onlyCurrentAndFuture) {
		if (onlyCurrentAndFuture) {
			return filterCurrentAndFutureReservations(reservationRepository.findByCustomerOrderByTimeFromAsc(customerService.getMyCustomer()));
		}
		return reservationRepository.findByCustomerOrderByTimeFromAsc(customerService.getMyCustomer());
	}

	public Reservation createReservation(Reservation reservation) throws ReservationException {
		if (reservation.getTimeFrom().after(reservation.getTimeTo())) {
			throw new ReservationException("dateTime validation error");
		}
		List<RestaurantTable> availableTables = restaurantTableService.getAllFreeTablesInTheDefinedTimePeriodForNumberOfPersons(reservation.getTimeFrom(), reservation.getTimeTo(), reservation.getPersons());
		boolean contained = false;
		for (RestaurantTable t : availableTables) {
			if (reservation.getTable().getPkIdRestaurantTable() == t.getPkIdRestaurantTable()) {
				contained = true;
			}
		}
		if (!contained) {
			throw new ReservationException("This table is not available at the defined time");
		}

		reservation.setCustomer(customerService.getMyCustomer());
		reservation.setPkIdReservation(0);
		reservation.setReservationstatus(new Reservationstatus(Reservationstatus.RESERVATIONSTATUS_VALID));
		return reservationRepository.saveAndFlush(reservation);

	}
}
