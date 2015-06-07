package application.service;

import application.dataaccess.ReservationRepository;
import application.dataaccess.RestaurantTableRepository;
import application.dataaccess.TableStatusRepository;
import application.domain.Customer;
import application.domain.Reservation;
import application.domain.RestaurantTable;
import application.domain.User;
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
            throw new CheckinException("No table found: [tableId="+tableId+"]");

        if (table.getTableStatus().getStatus().compareToIgnoreCase("free") != 0) {
            // table is not free
            throw new CheckinException("Table [tableId= "+tableId+"] is not free");
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Timestamp halfHourLater = new Timestamp(timestamp.getTime() + 30 * 60 * 1000); // plus 30 mins * 60 sec * 1000 millis
        /* Reservation reservation = reservationRepository.findByTableAndTimeFromLessThanAndTimeToGreaterThan
                (tableId, halfHourLater, halfHourLater);
        if (reservation != null && reservation.getCustomer() != user.getCustomer())
            // there is a reservation not made by this user that start in less than 30mins, or ends in more than 30mins
            throw new CheckinException("Table [tableId= "+tableId+"]: there is a reservation " +
                    "that starts in less than 30mins or ends in more than 30mins");

        // either, there is no reservation for this table - we are free to checkin
        // either, there is a reservation by this user that does not end in the next 30 min - free to checkin
        // either, there is a reservation by this user starting in less than 30 min - free to checkin

        // create a new reservation
        if (reservation == null){
           /* Vanja pls fix this lines.
            Reservation newRes = new Reservation();
            newRes.setFkIdUser(user.getPkIdUser());
            newRes.setTimeFrom(timestamp);
            newRes.setTimeTo(new Timestamp(timestamp.getTime() + 90 * 60 * 1000));
            newRes.setFkIdRestaurantTable(tableId);
            newRes.setPersons(1);
            newRes.setFkIdReservationStatus(1);

            reservationRepository.saveAndFlush(newRes);*/
       // }


        // change table status to occupied
        /*table.setFkIdTableStatus(tableStatusRepository.findByStatus("occupied").getPkIdTablestatus());
        tableRepository.saveAndFlush(table);*/

        return true;
    }

}
