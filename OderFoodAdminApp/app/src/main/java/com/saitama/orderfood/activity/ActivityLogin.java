package com.saitama.orderfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.saitama.orderfood.R;
import com.saitama.orderfood.model.UserModel;
import com.saitama.orderfood.task.LoginTask;

public class ActivityLogin extends AppCompatActivity {

    private long firstTime = 0;
    private Button btnLogin, btnLoginRegister;
    private TextInputEditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.findView();
        this.btnLogin.setOnClickListener(btnLoginOnClick);
        this.btnLoginRegister.setOnClickListener(btnRegisterOnClick);
    }

    private void findView() {
        btnLoginRegister = findViewById(R.id.btn_login_register);
        btnLogin = findViewById(R.id.btn_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    /**
     * Set OnClickListener cho "btnLoginRegister"
     */
    View.OnClickListener btnRegisterOnClick = v -> {
        Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
        startActivity(intent);
    };
    /**
     * Set OnClickListener cho "btnLogin"
     */
    View.OnClickListener btnLoginOnClick = v -> {
        if (isValid()) {
            String username = this.username.getText().toString();
            String password = this.password.getText().toString();
            UserModel userModel = new UserModel();
            userModel.setUsername(username);
            userModel.setPassword(password);

            LoginTask loginTask = new LoginTask(ActivityLogin.this);
            loginTask.execute(userModel);
        }
    };

    /**
     * Validate form đăng nhập
     */
    private boolean isValid() {
        boolean result = true;
        if (this.username.getText().length() == 0) {
            username.setError("Vui lòng điền SĐT");
            result = false;
        }
        if (this.password.getText().length() == 0) {
            password.setError("Vui lòng điền mật khẩu");
            result = false;
        }
        return result;
    }

    /**
     * Set onKeyDown cho nút "Back" ==> Nhấn 2 lần để thoát trương trình
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(ActivityLogin.this, "Nhấn lại để thoát khỏi chương trình", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                ActivityLogin.this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}