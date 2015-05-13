package application.dataaccess;


import application.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface ReservationRepository  extends JpaRepository<Reservation, Integer> {
}
