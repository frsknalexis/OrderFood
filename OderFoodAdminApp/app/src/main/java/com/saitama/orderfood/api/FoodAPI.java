package com.saitama.orderfood.api;

import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.FoodModel;
import com.saitama.orderfood.service.FoodService;
import com.saitama.orderfood.service.RetrofitClientCreator;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FoodAPI {
    private static final Retrofit retrofit = RetrofitClientCreator.getClient();

    public static JsonResult<List<FoodModel>> getList() {
        try {
            FoodService foodService = retrofit.create(FoodService.class);
            Call<JsonResult<List<FoodModel>>> call = foodService.getList();
            Response<JsonResult<List<FoodModel>>> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JsonResult<String> edit(RequestBody body) {
        try {
            FoodService foodService = retrofit.create(FoodService.class);
            Call<JsonResult<String>> call = foodService.edit(body);
            Response<JsonResult<String>> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static JsonResult<String> delete(RequestBody body) {
        try {
            FoodService foodService = retrofit.create(FoodService.class);
            Call<JsonResult<String>> call = foodService.delete(body);
            Response<JsonResult<String>> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
