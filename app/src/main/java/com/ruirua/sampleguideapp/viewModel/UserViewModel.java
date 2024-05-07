package com.ruirua.sampleguideapp.viewModel;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.ruirua.sampleguideapp.model.User;
import com.ruirua.sampleguideapp.repositories.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<List<User>> users;
    private Application app;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        users = userRepository.getAllUsers();
        app = application;
    }

    public LiveData<Boolean> postLogin(String username, String password){
        userRepository.login(username,password);
        return userRepository.getIsLoggedIn();
    }

    public void logout(){
        SharedPreferences sp = app.getSharedPreferences("BraGuia Shared Preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        // Delete every preference except the database last update
        editor.remove("cookies");
        editor.remove("user_type");
        editor.remove("notification_state");
        editor.remove("notification_distance");
        editor.remove("trail_running");
        editor.remove("service_running");
        editor.apply();

        // Delete user's database
        userRepository.delete();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }
}