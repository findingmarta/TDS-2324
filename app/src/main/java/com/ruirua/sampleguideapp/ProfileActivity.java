package com.ruirua.sampleguideapp;


import static android.text.TextUtils.concat;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.ruirua.sampleguideapp.model.User;
import com.ruirua.sampleguideapp.viewModel.UserViewModel;

import java.util.List;

public class ProfileActivity extends GeneralActivity {
    private TextView first_last_name;
    private TextView username;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_profile;
    }
    @Override
    protected int getNavBarItemSelected () {
        return R.id.Profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO Meter os butões a funcionar e ir buscar a info à BD

        first_last_name = findViewById(R.id.profile_name);
        username = findViewById(R.id.profile_username);

        setInfo();
    }

    public void setInfo() {
        UserViewModel uvm = new ViewModelProvider(this).get(UserViewModel.class);
        LiveData<List<User>> usersData = uvm.getUsers();

        usersData.observe(this, userslist -> {
            User user = userslist.get(0);
            assert user != null;
            String full_name = (String) concat(user.getFirst_name(),user.getLast_name());
            first_last_name.setText(full_name);
            username.setText(user.getUsername());
        });
    }

}
