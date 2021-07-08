package com.saitama.orderfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.saitama.orderfood.adapter.OrderAdapter;
import com.saitama.orderfood.model.OrderModel;
import com.saitama.orderfood.service.api.OrderApi;

import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private BottomNavigationView orderNavigationView;
    private RecyclerView recyclerOrder;
    private List<OrderModel> orderModelList;
    private ImageView icBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerOrder = findViewById(R.id.recycle_oder);


        init(0);
        orderNavigationView = findViewById(R.id.oder_navigation);
        orderNavigationView.setOnNavigationItemSelectedListener(navSelectedListener);

        icBack = findViewById(R.id.ic_order_back);
        icBack.setOnClickListener(v -> {
            finish();
        });
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navSelectedListener = item -> {

        switch (item.getItemId()) {
            case R.id.nav_0:
                init(0);
                break;
            case R.id.nav_1:
                init(1);
                break;
            case R.id.nav_2:
                init(2);
                break;
            case R.id.nav_3:
                init(3);
                break;
        }
        return true;
    };

    public void init(int status) {
        orderModelList = OrderApi.getOrderOfUser(status);
        OrderAdapter orderAdapter = new OrderAdapter(this, orderModelList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerOrder.setLayoutManager(layoutManager);
        recyclerOrder.setAdapter(orderAdapter);
    }

}