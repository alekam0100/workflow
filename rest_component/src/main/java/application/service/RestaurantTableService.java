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

	public RestaurantTable getTable(int tableId) {
		RestaurantTable table = tableRepository.findOne(tableId);
		return table;
	}

	public List<RestaurantTable> getAllTablesForNumberOfPersons(Integer numberOfPersons) {

		List<RestaurantTable> list = tableRepository.findByMaxPersonGreaterThanEqual(numberOfPersons);
		return list;
	}

	public List<RestaurantTable> getAllFreeTablesInTheDefinedTimePeriodForNumberOfPersons(Timestamp timeFrom, Timestamp timeTo, Integer numberOfPersons) {
		return filterFreeTablesInTheDefinedTimePeriod(timeFrom, timeTo, getAllTablesForNumberOfPersons(numberOfPersons));
	}


	private List<RestaurantTable> filterFreeTablesInTheDefinedTimePeriod(Timestamp timeFrom, Timestamp timeTo, List<RestaurantTable> tables) {
		List<RestaurantTable> toReturn = new ArrayList<RestaurantTable>();
		for (RestaurantTable t : tables) {
			if (checkTableReservations(t, timeFrom, timeTo)) {
				toReturn.add(t);
			}
		}

		return toReturn;
	}

	private boolean checkTableReservations(RestaurantTable table, Timestamp timeFrom, Timestamp timeTo) {

		if (getValidReservations(table.getPkIdRestaurantTable(), timeFrom, timeTo).isEmpty()) {
			return true;
		}
		return false;

	}


	private List<Reservation> getValidReservations(int tableId, Timestamp timeFrom, Timestamp timeTo) {
		List<Reservation> allTableŔeservations = reservationRepository.findByTableOrderByTimeFromAsc(getTable(tableId));
		List<Reservation> reservations = new ArrayList<Reservation>();
		for (Reservation r : allTableŔeservations) {
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
