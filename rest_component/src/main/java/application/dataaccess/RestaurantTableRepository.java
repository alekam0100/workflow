package application.dataaccess;


import application.domain.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Integer> {
    @Query("select t from RestaurantTable t where t.pkIdRestaurantTable = :tableId")
    RestaurantTable findById(@Param("tableId")int tableId);

    List<RestaurantTable> findByMaxPersonGreaterThanEqual(Integer numberOfPersons);
}
