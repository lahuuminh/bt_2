package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class LoginActivity extends AppCompatActivity {
    // Views
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton, changePasswordButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);

        setContentView(R.layout.activity_login);

        initViews();

    }

    private void initViews() {
        this.usernameEditText = findViewById(R.id.usernameEditText);
        this.passwordEditText = findViewById(R.id.passwordEditText);
        this.loginButton = findViewById(R.id.loginButton);
        this.changePasswordButton = findViewById(R.id.changePasswordButton);

        this.usernameEditText.setText("admin");

        this.loginButton.setOnClickListener(view -> {
            login();
        });

        this.changePasswordButton.setOnClickListener(view -> {
            Intent openChangePwActivity = new Intent(LoginActivity.this, ChangePasswordActivity.class);
            startActivity(openChangePwActivity);
        });
    }

    private void login() {
        String username = this.usernameEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();

        String savedPassword = sharedPreferences.getString("password", "123");

        // Validate input [1, 50] characters
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin Username & Password!", Toast.LENGTH_SHORT).show();

            if (username.isEmpty()) {
                this.usernameEditText.requestFocus();
            } else {
                this.passwordEditText.requestFocus();
            }
        } else if (username.length() > 50 || password.length() > 50) {
            Toast.makeText(this, "Username & Password! không hợp lệ!", Toast.LENGTH_SHORT).show();
        } else {
            // Username: admin, Password: 123
            if (username.equals("admin") && password.equals(savedPassword)) {
                Intent loginIntent = new Intent(this, InputActivity.class);
                startActivity(loginIntent);
            } else {
                Toast.makeText(this, "Username hoặc Password! không đúng, vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
            }
        }

    }
}