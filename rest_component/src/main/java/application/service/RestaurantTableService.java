package application.service;

import application.dataaccess.ReservationRepository;
import application.dataaccess.RestaurantTableRepository;
import application.domain.Reservation;
import application.domain.Reservationstatus;
import application.domain.RestaurantTable;
import application.exceptions.ConstraintsViolationException;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantTableService {

	@Autowired
	private RestaurantTableRepository tRepo;
	@Autowired
	private ReservationRepository rRepo;
	@Autowired
	private TokenManager tManager;
	@Autowired
	private TimeService timeService;

	public List<RestaurantTable> getAllTables() {
		return tRepo.findAll();
	}

	public RestaurantTable getTable(int tableId) throws ObjectNotFoundException {
		RestaurantTable table = tRepo.findOne(tableId);
		return table;
	}


	public List<RestaurantTable> getAllTablesForNumberOfPersons(Integer numberOfPersons) {

		List<RestaurantTable> list = tRepo.findByMaxPersonGreaterThanEqual(numberOfPersons);
		return list;
	}


	public List<RestaurantTable> getAllFreeTablesInTheDefinedTimePeriod(Timestamp timeFrom, Timestamp timeTo) throws ObjectNotFoundException, ConstraintsViolationException {
	return filterFreeTablesInTheDefinedTimePeriod(timeFrom, timeTo, getAllTables());
	}


	public List<RestaurantTable> getAllFreeTablesInTheDefinedTimePeriodForNumberOfPersons(Timestamp timeFrom, Timestamp timeTo, Integer numberOfPersons) throws ObjectNotFoundException, ConstraintsViolationException {
		return filterFreeTablesInTheDefinedTimePeriod(timeFrom, timeTo, getAllTablesForNumberOfPersons(numberOfPersons));
	}




	private List<RestaurantTable> filterFreeTablesInTheDefinedTimePeriod(Timestamp timeFrom, Timestamp timeTo, List<RestaurantTable> tables) throws ObjectNotFoundException {
		List<RestaurantTable> toReturn = new ArrayList<RestaurantTable>();
		for (RestaurantTable t : tables) {
			if (checkTableReservations(t, timeFrom, timeTo)) {
				toReturn.add(t);
			}
		}

		return toReturn;
	}

	private boolean checkTableReservations(RestaurantTable table, Timestamp timeFrom, Timestamp timeTo) throws ObjectNotFoundException {

		if (getValidReservations(table.getPkIdRestaurantTable(), timeFrom, timeTo).isEmpty()) {
			return true;
		}
		return false;

	}


	private List<Reservation> getValidReservations(int tableId, Timestamp timeFrom, Timestamp timeTo) throws ObjectNotFoundException {
		List<Reservation> allTableŔeservations = rRepo.findByTableOrderByTimeFromAsc(getTable(tableId));
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
