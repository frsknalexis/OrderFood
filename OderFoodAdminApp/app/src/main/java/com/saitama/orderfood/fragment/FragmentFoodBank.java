package com.saitama.orderfood.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saitama.orderfood.R;
import com.saitama.orderfood.activity.ActivityEditFood;
import com.saitama.orderfood.adapter.FoodBankAdapter;
import com.saitama.orderfood.api.FoodAPI;
import com.saitama.orderfood.model.FoodModel;

import java.util.List;

@SuppressLint("ValidFragment")
public class FragmentFoodBank extends Fragment {
    private RecyclerView recyclerFoodBank;
    private FoodBankAdapter foodBankAdapter;
    private FloatingActionButton btnFloatAddFood;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_food_bank, container, false);
        findView();
        new InitViewAsyncTask().execute();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new InitViewAsyncTask().execute();
    }

    private void findView() {
        recyclerFoodBank = view.findViewById(R.id.recycler_food_bank);
        btnFloatAddFood = view.findViewById(R.id.btn_float_add_food);

        btnFloatAddFood.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ActivityEditFood.class);
            startActivity(intent);
        });
    }

    private class InitViewAsyncTask extends AsyncTask<Void, Void, List<FoodModel>> {

        @Override
        protected List<FoodModel> doInBackground(Void... voids) {
            return FoodAPI.getList().getData();
        }

        @Override
        protected void onPostExecute(List<FoodModel> foodModels) {
            super.onPostExecute(foodModels);
            foodBankAdapter = new FoodBankAdapter(getContext(), foodModels);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerFoodBank.setLayoutManager(layoutManager);
            recyclerFoodBank.setAdapter(foodBankAdapter);
        }
    }

}