package application.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

import application.domain.Order;

public interface OrderRepository  extends JpaRepository<Order, Integer> {
}
