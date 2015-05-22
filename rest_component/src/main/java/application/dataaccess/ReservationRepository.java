package application.dataaccess;


import application.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;


//@Repository
public interface ReservationRepository  extends JpaRepository<Reservation, Integer> {
    @Query("select r from Reservation r where r.fkIdTable = ?1  and r.fkIdUser = ?2 and r.fkIdReservationstatus = 1 " +
            "and ?3 >= r.timeFrom and ?3 < r.timeTo")
    Reservation findByTableIdAndUserIdAndTime(int tableId, int userId, Timestamp time);
}
