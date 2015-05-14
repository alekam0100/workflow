package application.service;

import application.dataaccess.ReservationRepository;
import application.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * handles reservation of tables
 */
@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        reservations.add(new Reservation()); // TODO
        return reservations;
    }
}
