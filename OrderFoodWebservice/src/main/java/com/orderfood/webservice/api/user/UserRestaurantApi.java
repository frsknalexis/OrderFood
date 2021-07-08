package com.orderfood.webservice.api.user;

import com.orderfood.webservice.dto.JsonResult;
import com.orderfood.webservice.entity.RestaurantEntity;
import com.orderfood.webservice.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/restaurant")
public class UserRestaurantApi {
    @Autowired
    RestaurantService restaurantService;

    @GetMapping("listRestaurant")
    public JsonResult<List<RestaurantEntity>> getListRestaurant() {

        return new JsonResult<List<RestaurantEntity>>().success("Danh sách  nhà hàng", restaurantService.findAll());
    }

    @GetMapping("getRestaurant")
    public JsonResult<RestaurantEntity> getRestaurant(@RequestParam("id") long id,RestaurantEntity restaurantEntity){
        restaurantEntity=restaurantService.findById(id);
        return new JsonResult<RestaurantEntity>().success("ok",restaurantEntity);
    }
}
