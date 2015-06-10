package application.service;

import application.dataaccess.ReservationRepository;
import application.dataaccess.RestaurantTableRepository;
import application.domain.Reservation;
import application.domain.Reservationstatus;
import application.domain.RestaurantTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantTableService {

	@Autowired
	private RestaurantTableRepository tableRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	public List<RestaurantTable> getAllFreeTablesInTheDefinedTimePeriodForNumberOfPersons(Timestamp timeFrom, Timestamp timeTo, Integer numberOfPersons) {
		List<RestaurantTable> tables = tableRepository.findByMaxPersonGreaterThanEqual(numberOfPersons);
		List<RestaurantTable> toReturn = new ArrayList<RestaurantTable>();
		for (RestaurantTable t : tables) {
			if (getValidReservations(t.getPkIdRestaurantTable(), timeFrom, timeTo).isEmpty()) {
				toReturn.add(t);
			}
		}

		return toReturn;

	}

	private List<Reservation> getValidReservations(int tableId, Timestamp timeFrom, Timestamp timeTo) {
		List<Reservation> allTableReservations = reservationRepository.findByTableOrderByTimeFromAsc(tableRepository.findOne(tableId));
		List<Reservation> reservations = new ArrayList<Reservation>();
		for (Reservation r : allTableReservations) {
			if (((timeFrom.compareTo(r.getTimeFrom()) >= 0) && timeFrom.before(r.getTimeTo()))
					|| ((timeTo.compareTo(r.getTimeFrom()) > 0) && (timeTo.compareTo(r.getTimeTo()) <= 0))
					|| ((timeFrom.compareTo(r.getTimeFrom()) <= 0) && (timeTo.compareTo(r.getTimeTo()) >= 0))
					|| (timeFrom.equals(r.getTimeFrom()) || timeTo.equals(r.getTimeTo()))) {
				if (r.getReservationstatus().getPkIdReservationstatus() == Reservationstatus.RESERVATIONSTATUS_VALID) {
					reservations.add(r);
				}
			}
		}
		return reservations;
	}
}
