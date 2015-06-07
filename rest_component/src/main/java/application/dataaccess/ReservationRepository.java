package application.dataaccess;


import application.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;


//@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	/*   @Query("select r from Reservation r where r.fk_id_restaurant_table = ?1  and r.fk_id_reservation_status = 1 " +
            "and r.time_from <= ?2 and r.time_to > ?3")
    Reservation findByTableAndTimeFromLessThanAndTimeToGreaterThan(int tableId, Timestamp timeFrom, Timestamp timeTo);
*/
  //  List<Reservation> findByReservationstatusOrderByTimeFromAsc(Reservationstatus res);


}
