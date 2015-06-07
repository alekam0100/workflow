package application.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

import application.domain.Bill;

public interface BillRepository extends JpaRepository<Bill, Integer>  {

}
