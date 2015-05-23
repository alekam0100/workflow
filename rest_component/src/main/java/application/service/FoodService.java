package application.service;

import application.dataaccess.FoodRepository;
import application.domain.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * handles reservation of tables
 */
@Service
public class FoodService {

    @Autowired
    FoodRepository foodRepository;



    public List<Food> getAllFood() {
        List<Food> food = foodRepository.findAll();
        return food;
    }


}
