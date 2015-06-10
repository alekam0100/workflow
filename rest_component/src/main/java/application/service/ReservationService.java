package application.service;

import application.dataaccess.ReservationRepository;
import application.domain.Reservation;
import application.domain.Reservationstatus;
import application.domain.RestaurantTable;
import application.exceptions.ConstraintsViolationException;
import application.exceptions.ReservationException;
import application.validation.ReservationValidator;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

	@Autowired
	ReservationRepository reservationRepository;

	public Reservation getReservation(int id) {
		Reservation reservation = reservationRepository.findOne(id);

		return reservation;
	}

	public List<Reservation> getAllReservations(boolean onlyValid, boolean onlyCurrentAndFuture) {
		if (onlyValid) {
			List<Reservation> toReturn = reservationRepository.findByReservationstatusOrderByTimeFromAsc(new Reservationstatus(Reservationstatus.RESERVATIONSTATUS_VALID));
			if (onlyCurrentAndFuture) {
				return filterCurrentAndFutureReservations(toReturn);
			}
			return toReturn;

		} else {
			if (onlyCurrentAndFuture) {
				return filterCurrentAndFutureReservations(reservationRepository.findAll());
			}
			return reservationRepository.findAll();
		}
	}

	@Autowired
	private TimeService timeService;

	private List<Reservation> filterCurrentAndFutureReservations(List<Reservation> unfiltered) {
		List<Reservation> filtered = new ArrayList<Reservation>();
		for (Reservation r : unfiltered) {
			if (r.getTimeTo().after(timeService.getCurrentTimestamp())) {
				filtered.add(r);
			}
		}
		return filtered;
	}

	@Autowired
	CustomerService customerService;

	public List<Reservation> getMyReservations(boolean onlyCurrentAndFuture) {
		if (onlyCurrentAndFuture) {
			return filterCurrentAndFutureReservations(reservationRepository.findByCustomerOrderByTimeFromAsc(customerService.getMyCustomer()));
		}
		return reservationRepository.findByCustomerOrderByTimeFromAsc(customerService.getMyCustomer());
	}

	@Autowired
	RestaurantTableService restaurantTableService;

	@Autowired
	ReservationValidator rValidator;


	public Reservation createReservation(Reservation reservation) throws ReservationException, ConstraintsViolationException, ObjectNotFoundException, MissingServletRequestParameterException {

		List<RestaurantTable> availableTables = restaurantTableService.getAllFreeTablesInTheDefinedTimePeriodForNumberOfPersons(reservation.getTimeFrom(), reservation.getTimeTo(), reservation.getPersons());
		boolean contained = false;
		for (RestaurantTable t : availableTables) {
			if (reservation.getTable().getPkIdRestaurantTable() == t.getPkIdRestaurantTable()) {
				contained = true;
			}
		}
		if (!contained) {
			throw new ConstraintsViolationException("This table is not available at the defined time");
		}

		rValidator.validateRemoteReservationToBeAdded(reservation);
		reservation.setCustomer(customerService.getMyCustomer());
		reservation.setPkIdReservation(0);
		reservation.setReservationstatus(new Reservationstatus(Reservationstatus.RESERVATIONSTATUS_VALID));
		return reservationRepository.saveAndFlush(reservation);

	}
}
