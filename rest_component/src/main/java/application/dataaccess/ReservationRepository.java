package application.dataaccess;


import application.domain.Reservation;
import application.domain.Reservationstatus;
import application.domain.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;


public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    @Query("select r from Reservation r where r.table = ?1  and r.reservationstatus = ?2 " +
            "and r.timeFrom <= ?3 and r.timeTo> ?4")
    Reservation findByTableAndTimeFromLessThanAndTimeToGreaterThan(RestaurantTable table, Reservationstatus status, Timestamp timeFrom, Timestamp timeTo);
}
