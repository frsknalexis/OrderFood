package com.saitama.orderfood;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saitama.orderfood.adapter.AllFoodAdapter;
import com.saitama.orderfood.adapter.PopularFoodAdapter;
import com.saitama.orderfood.adapter.PopularRestaurantAdapter;
import com.saitama.orderfood.model.FoodModel;
import com.saitama.orderfood.model.RestaurantModel;
import com.saitama.orderfood.service.api.FoodApi;
import com.saitama.orderfood.service.api.RestaurantApi;

import java.util.List;
import java.util.Objects;

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {

    private RecyclerView recyclerRestaurantPopular;
    private RecyclerView recyclerFoodPopular;
    private RecyclerView recyclerFoodAll;
    private PopularRestaurantAdapter popularRestaurantAdapter;
    private PopularFoodAdapter popularFoodAdapter;
    private AllFoodAdapter allFoodAdapter;
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initPopularRestaurantView(Objects.requireNonNull(RestaurantApi.getAllRestaurant()).getData());
        initPopularFoodView(Objects.requireNonNull(FoodApi.getTop10()).getData());
        initAllFoodView(FoodApi.getAll().getData());
        return view;
    }


    private void initPopularRestaurantView(List<RestaurantModel> list) {
        recyclerRestaurantPopular = view.findViewById(R.id.recycler_restaurant_popular);
        popularRestaurantAdapter = new PopularRestaurantAdapter(getActivity(), list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerRestaurantPopular.setLayoutManager(layoutManager);
        recyclerRestaurantPopular.setAdapter(popularRestaurantAdapter);
    }

    private void initPopularFoodView(List<FoodModel> list) {
        recyclerFoodPopular = view.findViewById(R.id.recycler_food_popular);
        popularFoodAdapter = new PopularFoodAdapter(getActivity(), list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerFoodPopular.setLayoutManager(layoutManager);
        recyclerFoodPopular.setAdapter(popularFoodAdapter);
    }

    private void initAllFoodView(List<FoodModel> list) {
        recyclerFoodAll = view.findViewById(R.id.recycler_food_all);
        allFoodAdapter = new AllFoodAdapter(getActivity(), list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerFoodAll.setLayoutManager(layoutManager);
        recyclerFoodAll.setAdapter(allFoodAdapter);
    }
}