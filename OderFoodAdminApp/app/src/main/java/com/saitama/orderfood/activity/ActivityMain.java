package com.saitama.orderfood.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.saitama.orderfood.fragment.FragmentAccount;
import com.saitama.orderfood.fragment.FragmentFoodBank;
import com.saitama.orderfood.fragment.FragmentOrderFood;
import com.saitama.orderfood.R;

public class ActivityMain extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navSelectedListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, new FragmentOrderFood()).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getIntent().getExtras() != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, new FragmentOrderFood()).commit();
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.nav_home:
                selectedFragment = new FragmentOrderFood();
                break;
            case R.id.nav_cart:
                selectedFragment = new FragmentFoodBank();
                break;
            case R.id.nav_account:
                selectedFragment = new FragmentAccount();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, selectedFragment).commit();
        return true;
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Set onKeyDown cho nút "Back" ==> Nhấn 2 lần để thoát trương trình
     */
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(this, "Nhấn lại để thoát khỏi chương trình", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}