package application.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import application.domain.Order;
import application.domain.Reservation;

public interface OrderRepository  extends JpaRepository<Order, Integer> {

	List<Order> findByReservation(Reservation res);
}
