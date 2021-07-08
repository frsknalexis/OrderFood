package com.saitama.orderfood.service.client;

import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.RestaurantModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestaurantClient {
    @GET("/api/user/restaurant/listRestaurant")
    Call<JsonResult<List<RestaurantModel>>> getAllRestaurant();

    @GET("/api/user/restaurant/getRestaurant")
    Call<JsonResult<RestaurantModel>> getRestaurant(@Query("id") long id);
}
