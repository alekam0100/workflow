package application.dataaccess;


import application.domain.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CheckinRepository extends JpaRepository<Checkin, Integer> {
    @Query("select c from Checkin c where c.fkIdCustomer = ?1")
    List<Checkin> findByUser(int userId);
}
