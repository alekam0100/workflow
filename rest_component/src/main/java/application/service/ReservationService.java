package application.service;

import application.dataaccess.*;
import application.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RestaurantTableRepository tableRepository;

	@Autowired
	private TableStatusRepository tableStatusRepository;

	@Autowired
	private CheckinRepository checkinRepository;

	public Reservation getReservation(int id) {
		return reservationRepository.findOne(id);
	}

	public List<Reservation> getAllReservations(boolean onlyValid, boolean onlyCurrentAndFuture) {
		if (onlyValid) {

		//	List<Reservation> toReturn = reservationRepository.findByReservationstatusOrderByTimeFromAsc(new Reservationstatus(Reservationstatus.RESERVATIONSTATUS_VALID));
			List<Reservation> toReturn = reservationRepository.findAll(); //todo
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
		for(Reservation r: unfiltered) {
			if(r.getTimeTo().after(timeService.getCurrentTimestamp())) {
				filtered.add(r);
			}
		}
		return filtered;
	}
	public List<Reservation> getMyReservations(boolean bool) {
		List<Reservation> reservations = getAllReservations(false,false);//todo
		return reservations;
	}

	public Reservation createReservation(Reservation reservation) {
		return reservationRepository.saveAndFlush(reservation);
	}

	public boolean checkIn(int tableId, String token, Timestamp timestamp) throws Exception {
		User user = userRepo.findByToken(token);
		if (user == null)
			throw new Exception("No user found for specifed token = " + token);
		Customer customer = user.getCustomer();
		if (customer == null)
			throw new Exception("Specified user is not customer = " + user);

		RestaurantTable table = tableRepository.findById(tableId);
		if (table == null)
			throw new Exception("No table found for tableID = " + tableId);
		if (table.getTableStatus().getStatus().compareToIgnoreCase("free") != 0) {
			// table is not free
			return false;
		}

		Timestamp halfHourLater = new Timestamp(timestamp.getTime() + 30 * 60 * 1000); // plus 30 mins * 60 sec * 1000 millis
		Reservation reservation = reservationRepository.findByTableAndTimeFromLessThanAndTimeToGreaterThan
				(tableId, halfHourLater, halfHourLater);
		if (reservation != null && reservation.getCustomer() != user.getCustomer())
			// there is a reservation not made by this user that start in less than 30mins, or ends in more than 30mins
			return false;

		// either, there is no reservation for this table - we are free to checkin
		// either, there is a reservation by this user that does not end in the next 30 min - free to checkin
		// either, there is a reservation by this user starting in less than 30 min - free to checkin

		// insert into checkin table, and change table status to occupied
		Checkin checkin = new Checkin();
		checkin.setCustomer(customer);
		checkin.setRestaurantTable(table);
		checkinRepository.saveAndFlush(checkin);

		table.setFkIdTableStatus(tableStatusRepository.findByStatus("occupied").getPkIdTablestatus());
		tableRepository.saveAndFlush(table);

		return true;
	}

	public boolean isCheckedIn(String token) throws Exception {
		User user = userRepo.findByToken(token);
		if (user == null)
			throw new Exception("No user found for specifed token = " + token);
		Customer customer = user.getCustomer();
		if (customer == null)
			throw new Exception("Specified user is not customer = " + user);

		List<Checkin> checkins = checkinRepository.findByUser(user.getPkIdUser());
		if (checkins.isEmpty())
			throw new Exception("User did not checkin = " + user);

		return true;
	}
}
