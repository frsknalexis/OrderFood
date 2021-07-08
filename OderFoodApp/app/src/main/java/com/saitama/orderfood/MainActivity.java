package com.saitama.orderfood;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.saitama.orderfood.service.api.UserApi;
import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.SharedPrefsUtil;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navSelectedListener);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, new HomeFragment()).commit();
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navSelectedListener = item -> {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.nav_home:
                selectedFragment = new HomeFragment();
                break;
            case R.id.nav_cart:
                selectedFragment = new CartFragment();
                break;
            case R.id.nav_account:
                selectedFragment = new AccountFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, selectedFragment).commit();
        return true;
    };
}