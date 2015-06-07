package application.dataaccess;


import application.domain.Reservationstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ReservationstatusRepository extends JpaRepository<Reservationstatus, Integer> {

    @Query("select ts from Reservationstatus ts where ts.pkIdReservationstatus = ?1")
    Reservationstatus findByStatusId(int status);
}
