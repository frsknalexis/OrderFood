package com.saitama.orderfood.service.api;

import com.saitama.orderfood.model.OrderModel;
import com.saitama.orderfood.service.client.OrderClient;
import com.saitama.orderfood.utils.ContainsUtil;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class OrderApi {
    private  static final OrderClient orderClient = ContainsUtil.RETROFIT.create(OrderClient.class);
    /**
     * thêm đơn hàng
     *
     * @return void
     */
    public static void addOrder(String address) {
        try {
            Call<Void> call = orderClient.addOder(address);
             call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * lấy đơn hàng của user
     *
     * @return List < OrderModel>
     */
    public static List<OrderModel> getOrderOfUser(int status) {
        try {
            Call<List<OrderModel>> call = orderClient.getOrderOfUser(status);
            Response<List<OrderModel>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            return null;
        }
    }
}
