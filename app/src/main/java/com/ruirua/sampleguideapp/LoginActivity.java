package com.ruirua.sampleguideapp;


import android.content.Intent;
import android.content.SharedPreferences;
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

    SharedPreferences sp = getSharedPreferences("Braguia Shared Preferences", MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bind views
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);

        // Check if the user is logged in
        if (sp.getString("cookies", null) == null){
            // If NOT logged in send a POST request to the API
            login();
        } else{
            // If logged in start the homepage's activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void login(){
        UserViewModel uvm = new ViewModelProvider(this).get(UserViewModel.class);
        login_button.setOnClickListener(view -> uvm.postLogin(
                                                    username.getText().toString(),
                                                    password.getText().toString())
                                                    //findViewById(R.id.textView))  // TODO Controlo de erros no login
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        // User already logged in
        if (sp.getString("cookies", null) != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {}
}