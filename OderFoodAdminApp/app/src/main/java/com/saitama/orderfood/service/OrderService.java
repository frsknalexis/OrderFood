package com.saitama.orderfood.service;

import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.OrderModel;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderService {

    @GET("/api/admin/order")
    Call<List<OrderModel>> getByStatus(@Query("status") Integer status);

    @POST("/api/admin/order/confirm")
    Call<JsonResult<String>> confirm(@Body RequestBody body);
}
