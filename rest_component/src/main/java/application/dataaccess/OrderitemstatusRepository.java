package application.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

import application.domain.Orderitemstatus;

public interface OrderitemstatusRepository extends JpaRepository<Orderitemstatus, Integer> {

}
