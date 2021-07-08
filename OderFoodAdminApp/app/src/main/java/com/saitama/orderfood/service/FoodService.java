package com.saitama.orderfood.service;

import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.FoodModel;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface FoodService {

    /**
     * Lấy danh sách món ăn của quán
     */
    @GET("/api/admin/food")
    Call<JsonResult<List<FoodModel>>> getList();

    /**
     * Thêm món ăn cho chủ quán
     */
    @POST("/api/admin/food")
    Call<JsonResult<String>> edit(@Body RequestBody body);

    /**
     * Xóa món ăn cho chủ quán
     */
    @HTTP(method = "DELETE", path = "/api/admin/food", hasBody = true)
    Call<JsonResult<String>> delete(@Body RequestBody body);
}
