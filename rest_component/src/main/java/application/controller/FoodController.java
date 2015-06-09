package application.controller;


import application.domain.Food;
import application.domain.Foodtype;
import application.service.FoodService;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.restlet.util.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FoodController {

    @Autowired
    private FoodService foodService;

    public void food(@Header("org.restlet.http.headers") Series headers, Exchange exchange) throws Exception {

        final String BVG = "Beverages";
        final String DSH = "Dishes";
        Map<String, List<Map<String, Object>>> responseMap = new HashMap<>();
        responseMap.put(BVG, new ArrayList<Map<String, Object>>());
        responseMap.put(DSH, new ArrayList<Map<String, Object>>());

        for (Food f : foodService.getAllFood()) {
            String key;
            if (f.getFoodtype().getPkIdFoodtype() == Foodtype.FOODTYPE_BEVERAGE)
                key = BVG;
            else
                key = DSH;


            Map<String, Object> entry = new HashMap<String, Object>();
            entry.put("id", f.getPkIdFood());
            entry.put("name", f.getName());
            entry.put("price", f.getNetPrice());
            entry.put("available", f.getAvailable());
            entry.put("description", f.getDescription());
            entry.put("size", f.getSize() + " " + f.getSizeunit().getUnit());

            responseMap.get(key).add(entry);
        }


        exchange.getOut().setBody(responseMap);
    }

}
