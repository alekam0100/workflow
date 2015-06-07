package application.service;

import application.dataaccess.ReservationRepository;
import application.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

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
        for (Reservation r : unfiltered) {
            if (r.getTimeTo().after(timeService.getCurrentTimestamp())) {
                filtered.add(r);
            }
        }
        return filtered;
    }

    public List<Reservation> getMyReservations(boolean bool) {
        List<Reservation> reservations = getAllReservations(false, false);//todo
        return reservations;
    }

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.saveAndFlush(reservation);
    }
}
