package com.saitama.orderfood.service.api;

import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.RestaurantModel;
import com.saitama.orderfood.service.client.RestaurantClient;
import com.saitama.orderfood.utils.ContainsUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RestaurantApi {
    private static final   RestaurantClient restaurantClient = ContainsUtil.RETROFIT.create(RestaurantClient.class);
    /**
     * Lấy danh sách nhà hàng
     *
     *
     * @return JsonResponse<List<RestaurantModel>>
     */
    public static JsonResult<List<RestaurantModel>> getAllRestaurant(){
        try {
            Call<JsonResult<List<RestaurantModel>>> call = restaurantClient.getAllRestaurant();
            Response<JsonResult<List<RestaurantModel>>> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Lấy nhà hàng
     *
     *
     * @return JsonResponse<RestaurantModel>
     * @param id
     */
    public static JsonResult<RestaurantModel> getRestaurant(long id){
        try {

            Call<JsonResult<RestaurantModel>> call = restaurantClient.getRestaurant(id);
            Response<JsonResult<RestaurantModel>> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
