package com.saitama.orderfood.api;

import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.RestaurantModel;
import com.saitama.orderfood.service.RestaurantService;
import com.saitama.orderfood.service.RetrofitClientCreator;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestaurantAPI {

    private static final Retrofit retrofit = RetrofitClientCreator.getClient();

    /**
     * Đăng kí
     *
     * @param body RequestBody
     * @return JsonResponse<String>
     */
    public static JsonResult<RestaurantModel> register(RequestBody body) {
        try {
            RestaurantService restaurantService = retrofit.create(RestaurantService.class);
            Call<JsonResult<RestaurantModel>> call = restaurantService.register(body);
            Response<JsonResult<RestaurantModel>> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Kiểm tra người dùng đã đăng kí quán ăn chưa?
     */
    public static JsonResult<RestaurantModel> exists() {
        try {
            RestaurantService restaurantService = retrofit.create(RestaurantService.class);
            Call<JsonResult<RestaurantModel>> call = restaurantService.exists();
            Response<JsonResult<RestaurantModel>> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Đóng cửa quán ăn
     */
    public static JsonResult<RestaurantModel> close(RequestBody body) {
        try {
            RestaurantService restaurantService = retrofit.create(RestaurantService.class);
            Call<JsonResult<RestaurantModel>> call = restaurantService.close(body);
            Response<JsonResult<RestaurantModel>> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
