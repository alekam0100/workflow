package application.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import application.domain.Billstatus;
import application.domain.Order;
import application.domain.RestaurantTable;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
