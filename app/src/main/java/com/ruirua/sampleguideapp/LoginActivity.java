package com.ruirua.sampleguideapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ruirua.sampleguideapp.viewModel.UserViewModel;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login_button;
    private ProgressBar loading;


    public static final String name = "BraGuia Shared Preferences";
    public static final String key = "cookies";
    SharedPreferences sp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Setup back button handling
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {}
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        // Bind views
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);
        loading = findViewById(R.id.loading);
        loading.setVisibility(View.GONE);

        // Get preferences
        sp = getSharedPreferences(name, MODE_PRIVATE);
        if (sp.getString(key, null) != null){
            // If logged in start the homepage's activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else{
            // If NOT logged in send a POST request to the API
            login();
        }
    }

    public void login(){
        UserViewModel uvm = new ViewModelProvider(this).get(UserViewModel.class);
        login_button.setOnClickListener(view -> {
            loading.setVisibility(View.VISIBLE);

            uvm.postLogin(username.getText().toString(),password.getText().toString()).
                            observe(this,isLoggedIn -> {
                                if (isLoggedIn != null){
                                    if (isLoggedIn){
                                        // Start Homepage's Activity
                                        startMainActivity();
                                    }
                                    else{
                                        Toast.makeText(this, "Unable to log in with provided credentials.",Toast.LENGTH_LONG).show();
                                    }
                                    loading.setVisibility(View.GONE);
                                    // Close this Activity
                                    finish();
                                }
                            });
        });
    }

    public void startMainActivity ()  {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // User already logged in
        if (sp.getString(key, null) != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


}