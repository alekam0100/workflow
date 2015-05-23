package application.dataaccess;


import application.domain.TableStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TableStatusRepository extends JpaRepository<TableStatus, Integer> {
    @Query("select ts from TableStatus ts where ts.status = ?1")
    TableStatus findByStatus(String status);
}
