package application.service;

import application.dataaccess.ReservationRepository;
import application.dataaccess.RestaurantTableRepository;
import application.domain.*;
import application.exceptions.ConstraintsViolationException;
import application.exceptions.NoReservationInPeriodException;
import application.validation.ReservationValidator;
import application.validation.TableValidator;
import application.validation.TimeValidator;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;

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
	@Autowired
	private TableValidator tValidator;
	@Autowired
	private ReservationValidator rValidator;
	@Autowired
	private TimeValidator timeValidator;

	public List<RestaurantTable> getAllTables() {
		return tRepo.findAll();
	}

	public RestaurantTable getTable(int tableId) throws ObjectNotFoundException {
		RestaurantTable table = tRepo.findOne(tableId);
		tValidator.validateTableNotNull(table);
		return table;
	}


	public List<RestaurantTable> getAllTablesForNumberOfPersons(Integer numberOfPersons) {
		tValidator.validateNumberOfPersons(numberOfPersons);

		List<RestaurantTable> list = tRepo.findByMaxPersonGreaterThanEqual(numberOfPersons);
		return list;
	}


	public List<RestaurantTable> getAllFreeTablesInTheDefinedTimePeriod(Timestamp timeFrom, Timestamp timeTo) throws ObjectNotFoundException, ConstraintsViolationException {
		timeValidator.validateTimeInterval(timeFrom, timeTo);
		return filterFreeTablesInTheDefinedTimePeriod(timeFrom, timeTo, getAllTables());
	}


	public List<RestaurantTable> getAllFreeTablesInTheDefinedTimePeriodForNumberOfPersons(Timestamp timeFrom, Timestamp timeTo, Integer numberOfPersons) throws ObjectNotFoundException, ConstraintsViolationException {
		timeValidator.validateTimeInterval(timeFrom, timeTo);
		tValidator.validateNumberOfPersons(numberOfPersons);
		return filterFreeTablesInTheDefinedTimePeriod(timeFrom, timeTo, getAllTablesForNumberOfPersons(numberOfPersons));
	}


	public boolean checkTableAvailability(Reservation r, int tableId) throws ObjectNotFoundException {
		RestaurantTable tableToCheck = getTable(tableId);
		return checkTableReservations(tableToCheck, r.getTimeFrom(), r.getTimeTo());
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

	public boolean canISitDown(int tableId, Timestamp timeFrom, Timestamp timeTo) throws ObjectNotFoundException, ConstraintsViolationException {
		timeValidator.validateTimeInterval(timeFrom, timeTo);
		List<Reservation> reservations = getValidReservations(tableId, timeFrom, timeTo);
		if (reservations.isEmpty()) {
			return true;
		}
		if (reservations.size() == 1) {
			Reservation r = reservations.get(0);
			if (r.getTimeFrom().compareTo(timeFrom) <= 0) {
				if (r.getCustomer().getFkIdUser() == tManager.getCurrentUser().getPkIdUser()) {
					return true;
				}

				if (timeService.isTimeDifferenceGreaterThanTolerance(timeFrom, r.getTimeTo()) && getTable(tableId).getTablestatus().getPkIdTablestatus() == TableStatus.TABLESTATUS_OCCUPIED) {
					return false;
				} else {
					if (timeService.isTimeDifferenceGreaterThanTolerance(timeFrom, r.getTimeTo())) {
						if (!timeService.isTimeDifferenceGreaterThanTolerance(r.getTimeFrom(), timeFrom)) {
							return false;
						}
					}
					return true;
				}
			} else {
				if (r.getCustomer().getFkIdUser() == tManager.getCurrentUser().getPkIdUser()) {
					return true;
				}
				return false;
			}
		}
		for (int i = 0; i < reservations.size(); i++) {
			Reservation r = reservations.get(i);
			if (r.getTimeFrom().compareTo(timeFrom) <= 0) {
				if (r.getCustomer().getFkIdUser() == tManager.getCurrentUser().getPkIdUser()) {
					//assuming, that a reservation can't be older than 10 minutes
					return true;
				}
				if (timeService.isTimeDifferenceGreaterThanTolerance(timeFrom, r.getTimeTo()) && getTable(tableId).getTablestatus().getPkIdTablestatus() == TableStatus.TABLESTATUS_OCCUPIED) {
					return false;
				} else {
					if (timeService.isTimeDifferenceGreaterThanTolerance(timeFrom, r.getTimeTo())) {
						// this means, that the table has not the status occupied 
						return true;
					}
					//else: do nothing, go for next reservation (possible, maybe a user has checked in but nothing ordered, then the status is lateron at payment not set to Tablestatus.TABLESTATUS_FREE
				}
			} else {
				if (r.getCustomer().getFkIdUser() == tManager.getCurrentUser().getPkIdUser()) {
					return true;
				}
				return false;
			}
		}
		return false;
	}


	public List<Reservation> getCurrentOrNextReservationWithLogic(int tableId, Timestamp timeFrom, Timestamp timeTo) throws NoReservationInPeriodException, ObjectNotFoundException {
		List<Reservation> reservations = getValidReservations(tableId, timeFrom, timeTo);
		List<Reservation> toReturn = new ArrayList<Reservation>();
		if (reservations.isEmpty()) {
			throw new NoReservationInPeriodException();
		}
		List<Reservation> myReservations = filterMyReservations(reservations);

		if (reservations.size() == 1) {

			Reservation res = reservations.get(0);
			if (myReservations.contains(res)) {
				return reservations;
			}
			if (timeService.isTimeDifferenceGreaterThanTolerance(timeFrom, res.getTimeTo()) && getTable(tableId).getTablestatus().getPkIdTablestatus() == TableStatus.TABLESTATUS_OCCUPIED) {
				return null;
			} else {
				toReturn.add(res);
				return toReturn;
			}
		}

		for (int i = 0; i < reservations.size(); i++) {
			Reservation r = reservations.get(i);
			/*
			if(myReservations.contains(r)) {
					return r;
			} 
			*/
			if (r.getTimeFrom().compareTo(timeFrom) <= 0) {
				if (myReservations.contains(r)) {
					//assuming, that a valid reservation can't be older than 10 minutes, no further checks necessary
					toReturn.add(r);
					return toReturn;
				}
				if (timeService.isTimeDifferenceGreaterThanTolerance(timeFrom, r.getTimeTo()) && getTable(tableId).getTablestatus().getPkIdTablestatus() == TableStatus.TABLESTATUS_OCCUPIED) {
					return null;
				} else {
					toReturn.add(r);
				}
			} else {
				if (r.getCustomer().getFkIdUser() == tManager.getCurrentUser().getPkIdUser()) {
					toReturn.add(r);
					return toReturn;
				}
				return null;
			}
		}


		return null;

	}

	private List<Reservation> filterMyReservations(List<Reservation> reservations) {
		List<Reservation> result = new ArrayList<Reservation>();
		for (Reservation r : reservations) {
			if (r.getCustomer().getFkIdUser() == tManager.getCurrentUser().getPkIdUser()) {
				result.add(r);
			}
		}
		return result;
	}


	public Reservation checkIn(Reservation reservation) throws ConstraintsViolationException, ObjectNotFoundException, MissingServletRequestParameterException {
		rValidator.validateRemoteReservationToBeAdded(reservation);
		try {
			Reservation r;
			List<Reservation> resList = getCurrentOrNextReservationWithLogic(reservation.getTable().getPkIdRestaurantTable(), reservation.getTimeFrom(), reservation.getTimeTo());
			if (resList != null && resList.size() > 0 && resList.size() < 3) {
				r = resList.get(0);
				if (resList.size() == 1) {
					if (r.getCustomer().getFkIdUser() == tManager.getCurrentUser().getPkIdUser()) {
						if (r.getTimeFrom().compareTo(timeService.getCurrentTimestamp()) > 0) {
							r.setTimeFrom(timeService.getCurrentTimestamp());
							r.getTable().setTablestatus(new TableStatus(TableStatus.TABLESTATUS_OCCUPIED));
							rRepo.saveAndFlush(r);
						} else {
							RestaurantTable t = tRepo.getOne(r.getTable().getPkIdRestaurantTable());
							t.setTablestatus(new TableStatus(TableStatus.TABLESTATUS_OCCUPIED));
							tRepo.saveAndFlush(t);
						}
						return r;
					} else {
						r.setTimeTo(reservation.getTimeFrom());
						rRepo.saveAndFlush(r);
						return performCheckinWithoutExistingReservation(reservation);
					}
				} else {
					if (r.getCustomer().getFkIdUser() == tManager.getCurrentUser().getPkIdUser()) {
						//should never happen if implemented correctly
						throw new ConstraintsViolationException("Something went terribly wrong.");
					}
					r.setTimeTo(reservation.getTimeFrom());
					rRepo.saveAndFlush(r);
					Reservation my = resList.get(1);
					if (my.getCustomer().getFkIdUser() != tManager.getCurrentUser().getPkIdUser()) {
						//should never happen if implemented correctly
						throw new ConstraintsViolationException("Something went terribly wrong.");
					}
					my.setTimeFrom(reservation.getTimeFrom());
					my.getTable().setTablestatus(new TableStatus(TableStatus.TABLESTATUS_OCCUPIED));
					return rRepo.saveAndFlush(my);
				}

			} else {
				throw new ConstraintsViolationException("You cannot sit down here!");
			}
		} catch (NoReservationInPeriodException e) {
			return performCheckinWithoutExistingReservation(reservation);
		}


	}

	private Reservation performCheckinWithoutExistingReservation(Reservation reservation) {
		if (reservation.getCustomer() == null) {
			User u = tManager.getCurrentUser();
			reservation.setCustomer(new Customer());
			reservation.getCustomer().setUser(u);
			reservation.getCustomer().setFkIdUser(u.getPkIdUser());
		}
		reservation.setPkIdReservation(0);
		reservation.getTable().setTablestatus(new TableStatus(TableStatus.TABLESTATUS_OCCUPIED));
		reservation.setReservationstatus(new Reservationstatus(Reservationstatus.RESERVATIONSTATUS_VALID));
		Reservation returnR = rRepo.saveAndFlush(reservation);
		return returnR;

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

	public RestaurantTable editTable(int tableId, RestaurantTable table) throws ObjectNotFoundException, ConstraintsViolationException, IllegalArgumentException {
		tValidator.validateTableNotNull(table);
		tValidator.validateTable(tableId, table);
		if (!tRepo.exists(tableId)) {
			throw new ObjectNotFoundException("Table not found!");
		}
		return tRepo.saveAndFlush(table);
	}

}
