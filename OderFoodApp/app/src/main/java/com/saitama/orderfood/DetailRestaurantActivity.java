package com.saitama.orderfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.saitama.orderfood.adapter.SimilarFoodAdater;
import com.saitama.orderfood.model.FoodModel;
import com.saitama.orderfood.model.RestaurantModel;
import com.saitama.orderfood.service.api.FoodApi;
import com.saitama.orderfood.service.api.RestaurantApi;
import com.saitama.orderfood.utils.ContainsUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailRestaurantActivity extends AppCompatActivity {
    private RecyclerView recyclerSimilar;
    private TextView tvName, tvAddress;
    private ImageView img;
    private RestaurantModel restaurantModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detal_restaurant);

        setIcBack();

        Bundle b = getIntent().getExtras().getBundle("bundle");
        long id = b.getLong("id");
        restaurantModel = RestaurantApi.getRestaurant(id).getData();
        findAndSetView();
        init(FoodApi.getFoodOfRestaurant(id).getData());

    }

    private void findAndSetView() {
        recyclerSimilar = findViewById(R.id.recycle_similar);
        tvName = findViewById(R.id.name_detailRestaurant);
        tvAddress = findViewById(R.id.address_detailRestaurant);
        img = findViewById(R.id.img_detailRestaurant);

        tvName.setText(restaurantModel.getName());
        tvAddress.setText(restaurantModel.getAddress());
        Picasso.get()
                .load(ContainsUtil.URL + restaurantModel.getAvatar())
                .resize(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().widthPixels / 2)
                .centerCrop()
                .into(img);
    }

    private void init(List<FoodModel> data) {
        SimilarFoodAdater similarFoodAdater = new SimilarFoodAdater(this, data);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);

        recyclerSimilar.setLayoutManager(layoutManager);
        recyclerSimilar.setAdapter(similarFoodAdater);
    }

    public void setIcBack() {
        ImageView icBack = findViewById(R.id.ic_back);
        icBack.setOnClickListener(v -> finish());
    }
}