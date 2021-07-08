package com.saitama.orderfood.service.api;

import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.UserModel;
import com.saitama.orderfood.service.client.CartClient;
import com.saitama.orderfood.service.client.RetrofitClient;
import com.saitama.orderfood.service.client.UserClient;
import com.saitama.orderfood.utils.ContainsUtil;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserApi {
    private static final Retrofit retrofit = RetrofitClient.getClient();

    /**
     * Lấy thông tin sinh viên
     *
     * @param userModel userModel
     * @return JsonResponse<String>
     */
    public static JsonResult<String> login(UserModel userModel) {
        try {
            UserClient userClient = retrofit.create(UserClient.class);
            Call<JsonResult<String>> call = userClient.login(userModel);
            Response<JsonResult<String>> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lưu token firebase cloud message sau khi đăng nhập thành công
     */
    public static JsonResult<String> registerTokenFCM(RequestBody body) {
        try {
            UserClient userClient = retrofit.create(UserClient.class);
            Call<JsonResult<String>> call = userClient.registerTokenFCM(body);
            Response<JsonResult<String>> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy thông tin tài khoản
     */
    public static JsonResult<UserModel> getInfo() {
        try {
            UserClient userClient = retrofit.create(UserClient.class);
            Call<JsonResult<UserModel>> call = userClient.getInfo();
            Response<JsonResult<UserModel>> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Đăng kí
     *
     * @param body RequestBody
     * @return JsonResponse<String>
     */
    public static JsonResult<String> register(RequestBody body) {
        try {
            UserClient userClient = retrofit.create(UserClient.class);
            Call<JsonResult<String>> call = userClient.register(body);
            Response<JsonResult<String>> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
