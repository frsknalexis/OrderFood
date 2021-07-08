package com.saitama.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.saitama.orderfood.model.UserModel;
import com.saitama.orderfood.task.LoginTask;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        btnLogin.setOnClickListener(v -> {
            String username = this.username.getText().toString();
            String password = this.password.getText().toString();
            UserModel userModel = new UserModel();
            userModel.setUsername(username);
            userModel.setPassword(password);
            LoginTask loginTask = new LoginTask(LoginActivity.this);
            loginTask.execute(userModel);
        });

        btnRegister = findViewById(R.id.btn_login_register);
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(LoginActivity.this, "Nhấn lại để thoát khỏi chương trình", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                LoginActivity.this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}