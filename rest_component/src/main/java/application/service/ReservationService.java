package application.service;

import application.dataaccess.*;
import application.domain.*;
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

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RestaurantTableRepository tableRepository;

    @Autowired
    private TableStatusRepository tableStatusRepository;

    @Autowired
    private CheckinRepository checkinRepository;

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
        if (reservation != null && reservation.getFkIdUser() != user.getPkIdUser())
            // there is a reservation not made by this user in less than 30mins
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

    public boolean isCheckedIn(String token) throws Exception{
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
