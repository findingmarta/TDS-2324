package com.ruirua.sampleguideapp;


import static android.text.TextUtils.concat;

import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
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

    private void setInfo() {
        UserViewModel avm = new ViewModelProvider(this).get(UserViewModel.class);
        LiveData<List<User>> userData = avm.getUsers();
        userData.observe(this, userslist -> {
            // TODO Aplicado só a um user precisa de ser a todos
            User user = userslist.get(0);
            String full_name = (String) concat(user.getFirst_name(),user.getLast_name());
            first_last_name.setText(full_name);
            username.setText(user.getUsername());
        });
    }

}
