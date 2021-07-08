package com.saitama.orderfood.service.client;

import com.saitama.orderfood.model.OrderModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderClient {

    @POST("/api/user/order/addOrder")
    Call<Void> addOder(@Query("address") String address);

    @GET("/api/user/order/getOrderOfUser")
    Call<List<OrderModel>> getOrderOfUser(@Query("status")int status);
}
