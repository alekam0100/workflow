package application.dataaccess;


import application.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;


//@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    @Query("select r from Reservation r where r.fkIdRestaurantTable = ?1  and r.fkIdReservationStatus = 1 " +
            "and r.timeFrom <= ?2 and r.timeTo > ?3")
    Reservation findByTableAndTimeFromLessThanAndTimeToGreaterThan(int tableId, Timestamp timeFrom, Timestamp timeTo);

  //  List<Reservation> findByReservationstatusOrderByTimeFromAsc(Reservationstatus res);


}
