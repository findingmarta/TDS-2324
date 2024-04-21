package com.ruirua.sampleguideapp;


import static android.text.TextUtils.concat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.ruirua.sampleguideapp.model.User;
import com.ruirua.sampleguideapp.viewModel.UserViewModel;

import java.util.List;

public class ProfileActivity extends GeneralActivity {
    private TextView first_last_name;
    private TextView username;
    private ImageView premium_logo;
    private Button profile_history_button;
    private Button profile_settings_button;
    private boolean isPremium = false;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_profile;
    }
    @Override
    protected int getNavBarItemSelected () {
        return R.id.Profile;
    }

    @Override
    protected void onGeneralActivityCreate() {
        first_last_name = findViewById(R.id.profile_name);
        username = findViewById(R.id.profile_username);
        premium_logo = findViewById(R.id.profile_premium);
        profile_history_button = findViewById(R.id.profile_history_button);
        profile_settings_button = findViewById(R.id.profile_settings_button);

        setInfo();
    }

    public void setInfo() {
        UserViewModel uvm = new ViewModelProvider(this).get(UserViewModel.class);
        LiveData<List<User>> usersData = uvm.getUsers();

        usersData.observe(this, userslist -> {
            User user = userslist.get(0);
            if (user != null){
                isPremium = user.getUser_type().equals("Premium");

                String full_name = (String) concat(user.getFirst_name(),user.getLast_name());
                first_last_name.setText(full_name);
                username.setText(String.format("@%s", user.getUsername()));

                // If its not a premium user hide the history button and the verified tag
                if (!isPremium){
                    profile_history_button.setVisibility(View.GONE);
                    premium_logo.setVisibility(View.GONE);
                } else{
                    setHistoryButton();
                    // TODO Meter o botão das settings a funcionar (só para premium?)
                }
            }
        });
    }

    public void setHistoryButton(){
        profile_history_button.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }

}
