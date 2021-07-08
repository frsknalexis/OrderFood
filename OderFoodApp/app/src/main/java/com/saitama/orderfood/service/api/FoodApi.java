package com.saitama.orderfood.service.api;

import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.FoodModel;
import com.saitama.orderfood.service.client.FoodClient;
import com.saitama.orderfood.utils.ContainsUtil;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class FoodApi {
    private  static final   FoodClient foodClient = ContainsUtil.RETROFIT.create(FoodClient.class);
    /**
     * Lấy danh sách top 10 món ăn
     *
     * @return JsonResponse<List < FoodModel>>
     */
    public static JsonResult<List<FoodModel>> getTop10() {
        try {
            Call<JsonResult<List<FoodModel>>> call = foodClient.getTop10();
            Response<JsonResult<List<FoodModel>>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            return null;
        }
    }
    /**
     * Lấy danh sách tất cả món ăn
     *
     * @return JsonResponse<List < FoodModel>>
     */
    public static JsonResult<List<FoodModel>> getAll() {
        try {
            Call<JsonResult<List<FoodModel>>> call = foodClient.getAll();
            Response<JsonResult<List<FoodModel>>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            return null;
        }
    }
    /**
     * Lấy danh sách tất cả món ăn của nhà hàng theo id
     *
     * @return JsonResponse<List < FoodModel>>
     */
    public static JsonResult<List<FoodModel>> getFoodOfRestaurant(long id) {
        try {
            Call<JsonResult<List<FoodModel>>> call = foodClient.getFoodOfRestaurant(id);
            Response<JsonResult<List<FoodModel>>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            return null;
        }
    }
}
