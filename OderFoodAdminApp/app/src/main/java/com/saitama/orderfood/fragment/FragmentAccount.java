package com.saitama.orderfood.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.saitama.orderfood.R;
import com.saitama.orderfood.activity.ActivityLogin;
import com.saitama.orderfood.activity.ActivityRegRestaurant;
import com.saitama.orderfood.activity.ActivityRegister;
import com.saitama.orderfood.model.RestaurantModel;
import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.PicassoCircleTransformation;
import com.saitama.orderfood.utils.SharedPrefsUtil;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class FragmentAccount extends Fragment {

    private TextView txtAccountInfo, txtRestaurantInfo;
    private Button btnLogout;
    private ImageView imgAvatarRestaurant;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        findView();

        initView();
        changeAccountInfo();
        changeRestaurantInfo();
        logout();

        return view;
    }

    private void findView() {
        btnLogout = view.findViewById(R.id.btn_logout);
        imgAvatarRestaurant = view.findViewById(R.id.img_avatar_restaurant);
        txtAccountInfo = view.findViewById(R.id.txt_account_info);
        txtRestaurantInfo = view.findViewById(R.id.txt_restaurant_info);
    }

    /**
     * Khởi tạo view
     */
    private void initView() {
        RestaurantModel restaurantModel = SharedPrefsUtil.getInstance().get(ContainsUtil.RESTAURANT_CLIENT, RestaurantModel.class);
        String avatar = ContainsUtil.SERVICE_URL + restaurantModel.getAvatar();
        Picasso
                .get()
                .load(avatar)
                .transform(new PicassoCircleTransformation())
                .resize(150, 150)
                .centerCrop()
                .into(imgAvatarRestaurant);
    }

    /**
     * Xử lý chuyển activity
     */
    private void changeAccountInfo() {
        txtAccountInfo.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ActivityRegister.class);
            startActivity(intent);
        });
    }

    private void changeRestaurantInfo() {
        txtRestaurantInfo.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ActivityRegRestaurant.class);
            startActivity(intent);
        });
    }

    /**
     * Xử lý button đăng xuất
     */
    private void logout() {
        this.btnLogout.setOnClickListener(v -> {
            SharedPrefsUtil.getInstance().remove(ContainsUtil.TOKEN_LOGIN);
            SharedPrefsUtil.getInstance().remove(ContainsUtil.RESTAURANT_CLIENT);
            Intent intent = new Intent(getContext(), ActivityLogin.class);
            startActivity(intent);
            Objects.requireNonNull(this.getActivity()).finish();
        });
    }
}