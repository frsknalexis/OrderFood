package com.saitama.orderfood.service.api;

import com.saitama.orderfood.model.CartModel;
import com.saitama.orderfood.service.client.CartClient;
import com.saitama.orderfood.utils.ContainsUtil;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class CartApi {
    private static final CartClient CART_CLIENT = ContainsUtil.RETROFIT.create(CartClient.class);

    /**
     * Thêm món ăn vào giỏ hàng
     *
     * @return void
     */
    public static Double addCart(long idProduct, String amount) {
        try {
            Call<Double> call = CART_CLIENT.addCart(idProduct, amount);
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return 0D;
        }
    }

    /**
     * xóa sản phẩm khỏi giỏ hàng
     *
     * @return void
     */
    public static Double delCart(long idCart) {
        try {
            Call<Double> call = CART_CLIENT.deleteCart(idCart);
            Response<Double> response = call.execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
            return 0D;
        }
    }

    /**
     * Lấy danh sách tất cả món ăn
     *
     * @return List < CartModel>
     */
    public static List<CartModel> getCartOfUser() {
        try {
            Call<List<CartModel>> call = CART_CLIENT.getCartOfUser();
            Response<List<CartModel>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            return null;
        }
    }
}
