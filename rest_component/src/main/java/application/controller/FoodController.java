package application.controller;


import application.domain.Food;
import application.service.FoodService;
import org.apache.camel.Header;
import org.restlet.util.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FoodController {

    @Autowired
    private FoodService foodService;

    public List<String> food(@Header("org.restlet.http.headers") Series headers) throws Exception{
        List<String> result = new ArrayList<String>();
        List<Food> food = foodService.getAllFood();
        for(Food f: food){
            result.add(f.toString());
        }
        return result;
    }

}
