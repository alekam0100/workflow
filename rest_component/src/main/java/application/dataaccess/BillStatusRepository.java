package application.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

import application.domain.Billstatus;

public interface BillStatusRepository  extends JpaRepository<Billstatus, Integer> {

}
