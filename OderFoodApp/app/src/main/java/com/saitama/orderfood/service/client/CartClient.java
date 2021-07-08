package com.saitama.orderfood.service.client;


import com.saitama.orderfood.model.CartModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface CartClient {

    @PUT("/api/user/cart/add")
    Call<Double> addCart(@Query("idFood") long idFood, @Query("amount") String amount);

    @GET("/api/user/cart/getCartOfUser")
    Call<List<CartModel>> getCartOfUser();

    @DELETE("/api/user/cart/delete")
    Call<Double> deleteCart(@Query("idCart") long idCart);
}
