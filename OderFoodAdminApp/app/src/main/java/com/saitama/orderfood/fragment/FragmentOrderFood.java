package com.saitama.orderfood.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.saitama.orderfood.R;
import com.saitama.orderfood.adapter.OrderFoodAdapter;
import com.saitama.orderfood.api.OrderAPI;
import com.saitama.orderfood.model.OrderModel;

import java.util.List;

@SuppressLint("ValidFragment")
public class FragmentOrderFood extends Fragment {

    private View view;
    private BottomNavigationView orderNavigationView;
    private Integer idNav;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        orderNavigationView = view.findViewById(R.id.order_navigation);
        orderNavigationView.setOnNavigationItemSelectedListener(navSelectedListener);
        new OrderListTask().execute(0);
        idNav = 0;
        return view;
    }

    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener navSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.nav_order_0:
                idNav = 0;
                new OrderListTask().execute(0);
                break;
            case R.id.nav_order_1:
                idNav = 1;
                new OrderListTask().execute(1);
                break;
            case R.id.nav_order_2:
                idNav = 2;
                new OrderListTask().execute(2);
                break;
            case R.id.nav_order_3:
                idNav = 3;
                new OrderListTask().execute(3);
                break;
        }
        return true;
    };

    @SuppressLint("StaticFieldLeak")
    class OrderListTask extends AsyncTask<Integer, Void, List<OrderModel>> {

        private RecyclerView recyclerOrderFood;
        private OrderFoodAdapter orderFoodAdapter;
        private ProgressDialog dialog;

        public OrderListTask() {
            dialog = new ProgressDialog(getActivity());
            recyclerOrderFood = view.findViewById(R.id.recycler_order_food);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected List<OrderModel> doInBackground(Integer... integers) {
            return OrderAPI.getByStatus(integers[0]);
        }

        @Override
        protected void onPostExecute(List<OrderModel> orderModels) {
            super.onPostExecute(orderModels);
            orderFoodAdapter = new OrderFoodAdapter(getActivity(), orderModels, idNav);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerOrderFood.setLayoutManager(layoutManager);
            recyclerOrderFood.setAdapter(orderFoodAdapter);

            if (dialog.isShowing())
                dialog.dismiss();

        }
    }

}