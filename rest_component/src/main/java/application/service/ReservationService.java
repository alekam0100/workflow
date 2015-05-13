package application.service;

import application.dataaccess.ReservationRepository;
import application.domain.Reservation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    //@Autowired
    ReservationRepository reservationRepository;

    public List<Reservation> getAllReservations() {
        List<Reservation> output = new ArrayList<>();
        output.add(new Reservation());
        return output;
    }
}
