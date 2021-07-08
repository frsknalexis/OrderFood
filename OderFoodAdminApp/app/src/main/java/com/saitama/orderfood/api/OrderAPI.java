package com.saitama.orderfood.api;

import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.OrderModel;
import com.saitama.orderfood.service.OrderService;
import com.saitama.orderfood.service.RetrofitClientCreator;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderAPI {

    private static final Retrofit retrofit = RetrofitClientCreator.getClient();

    /**
     * Lấy danh sách đơn hàng theo trạng thái
     *
     * @param status status
     * @return List<RestaurantModel>  - Trả về danh sách
     */
    public static List<OrderModel> getByStatus(Integer status) {
        try {
            OrderService orderService = retrofit.create(OrderService.class);
            Call<List<OrderModel>> call = orderService.getByStatus(status);
            Response<List<OrderModel>> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Xác nhận đơn hàng
     *
     * @param body RequestBody
     * @return List<RestaurantModel>  - Trả về danh sách
     */
    public static JsonResult<String> confirm(RequestBody body) {
        try {
            OrderService orderService = retrofit.create(OrderService.class);
            Call<JsonResult<String>> call = orderService.confirm(body);
            Response<JsonResult<String>> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
