package com.ruirua.sampleguideapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.viewModel.LoginViewModel;
import com.ruirua.sampleguideapp.viewModel.PointViewModel;
import com.ruirua.sampleguideapp.viewModel.UserViewModel;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bind views
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);

        // Check if the user is logged in

        // If NOT logged in Post the login
        login();

        // If logged in start the homepage's activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void login(){
        LoginViewModel lvm = new ViewModelProvider(this).get(LoginViewModel.class);
        login_button.setOnClickListener(view ->
                lvm.postLogin(username.getText().toString(),
                        password.getText().toString())
                        //findViewById(R.id.textView))
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        // If loggen in
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {}
}