package com.saitama.orderfood.service.client;

import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.FoodModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodClient {
    @GET("/api/user/food/getTop10")
    Call<JsonResult<List<FoodModel>>> getTop10();

    @GET("/api/user/food/getAll")
    Call<JsonResult<List<FoodModel>>> getAll();

    @GET("/api/user/food/getFoodOfRestaurant")
    Call<JsonResult<List<FoodModel>>> getFoodOfRestaurant(@Query("id") long id);
}
