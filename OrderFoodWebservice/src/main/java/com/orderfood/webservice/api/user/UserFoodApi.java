package com.orderfood.webservice.api.user;

import com.orderfood.webservice.dto.JsonResult;
import com.orderfood.webservice.entity.FoodEntity;
import com.orderfood.webservice.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/user/food")
public class UserFoodApi {
    @Autowired
    FoodService foodService;

    @GetMapping("getTop10")
    public JsonResult<List<FoodEntity>> getTop10() {
        Pageable pageable = PageRequest.of(0, 10);
        for (FoodEntity f : foodService.getTop10(pageable)) {
            System.out.println("----------------------------------------");
            System.out.println(f.getName());
        }
        return new JsonResult<List<FoodEntity>>().success("top 10 món ăn", foodService.getTop10(pageable));
    }

    @GetMapping("getAll")
    public JsonResult<List<FoodEntity>> getAll() {
        return new JsonResult<List<FoodEntity>>().success("danh sách tất cả món ăn", foodService.findAll());
    }

    @GetMapping("getFoodOfRestaurant")
    public JsonResult<List<FoodEntity>> getFoodOfRestaurant(@RequestParam("id") long id) {
        return new JsonResult<List<FoodEntity>>().success("ok", foodService.findByRestaurant_Id(id));
    }
}
