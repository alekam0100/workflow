package application.service;

import application.dataaccess.ReservationRepository;
import application.dataaccess.ReservationstatusRepository;
import application.dataaccess.RestaurantTableRepository;
import application.dataaccess.TableStatusRepository;
import application.domain.*;
import application.exceptions.CheckinException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * handles reservation of tables
 */
@Service
public class CheckinService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ReservationstatusRepository reservationstatusRepository;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private RestaurantTableRepository tableRepository;

    @Autowired
    private TableStatusRepository tableStatusRepository;

    public boolean checkIn(int tableId) throws CheckinException {
        User user = tokenManager.getCurrentUser();
        if (user == null)
            throw new CheckinException("No current user found");

        Customer customer = user.getCustomer();
        if (customer == null)
            throw new CheckinException("Current user is not a customer");

        RestaurantTable table = tableRepository.findById(tableId);
        if (table == null)
            throw new CheckinException("No table found: [tableId=" + tableId + "]");

        if (table.getTableStatus().getStatus().compareToIgnoreCase("free") != 0) {
            // table is not free
            throw new CheckinException("Table [tableId= " + tableId + "] is not free");
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis() + 30 * 1000); // now plus 30 sec
        Timestamp halfHourLater = new Timestamp(timestamp.getTime() + 30 * 60 * 1000); // plus 30 mins * 60 sec * 1000 millis

        Reservationstatus validStatus = reservationstatusRepository.findByStatusId(Reservationstatus.RESERVATIONSTATUS_VALID);
        Reservation reservation = reservationRepository.findByTableAndTimeFromLessThanAndTimeToGreaterThan
                (table, validStatus, halfHourLater, halfHourLater);
        if (reservation != null && reservation.getCustomer() != user.getCustomer())
            // there is a reservation not made by this user that start in less than 30mins, or ends in more than 30mins
            throw new CheckinException("Table [tableId= " + tableId + "]: there is a reservation " +
                    "that starts in less than 30mins or ends in more than 30mins");

        // either, there is no reservation for this table - we are free to checkin
        // either, there is a reservation by this user that does not end in the next 30 min - free to checkin
        // either, there is a reservation by this user starting in less than 30 min - free to checkin

        // create a new reservation
        if (reservation == null) {
            Reservation newRes = new Reservation();
            newRes.setCustomer(customer);
            newRes.setTimeFrom(timestamp);
            newRes.setTimeTo(new Timestamp(timestamp.getTime() + 90 * 60 * 1000));
            newRes.setTable(table);
            newRes.setPersons(1);
            newRes.setReservationstatus(validStatus);

            reservationRepository.saveAndFlush(newRes);
        }


        // change table status to occupied
        table.setTablestatus(tableStatusRepository.findByStatus("occupied"));
        tableRepository.saveAndFlush(table);

        return true;
    }

}
