package application.controller;

import application.domain.Reservation;
import application.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }
}
