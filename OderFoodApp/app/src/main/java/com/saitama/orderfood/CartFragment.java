package com.saitama.orderfood;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saitama.orderfood.adapter.CartAdapter;
import com.saitama.orderfood.model.CartModel;
import com.saitama.orderfood.service.api.CartApi;
import com.saitama.orderfood.service.api.OrderApi;
import com.saitama.orderfood.utils.ContainsUtil;

import java.util.List;

@SuppressLint("ValidFragment")
public class CartFragment extends Fragment {

    private RecyclerView recyclerCart;
    private CartAdapter cartAdapter;
    private TextView tvTotalPrice;
    private EditText edAddress;
    private Button btOrder;
    private List<CartModel> cartModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        tvTotalPrice = view.findViewById(R.id.tv_cart_total_price);
        edAddress = view.findViewById(R.id.tv_cart_address);
        btOrder = view.findViewById(R.id.bt_order_food);

        cartModelList = CartApi.getCartOfUser();
        btOrder();
        setView();

        recyclerCart = view.findViewById(R.id.recycler_cart);
        init();
        return view;
    }

    public void init() {
        cartAdapter = new CartAdapter(getActivity(), cartModelList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerCart.setLayoutManager(layoutManager);
        recyclerCart.setAdapter(cartAdapter);
    }

    public void btOrder() {
        btOrder.setOnClickListener(v -> {
            OrderApi.addOrder(edAddress.getText().toString());
            cartModelList = CartApi.getCartOfUser();
            setView();
            init();
            edAddress.setText("");
            Toast.makeText(getActivity(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
        });
    }

    public void setView() {
        double totalPrice = 0;
        for (CartModel c : cartModelList) {
            totalPrice += c.getAmount() * c.getFood().getPrice();
        }
        tvTotalPrice.setText(ContainsUtil.formatCurrency(totalPrice));
    }
}