package application.dataaccess;


import application.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FoodRepository extends JpaRepository<Food, Integer> {

}
