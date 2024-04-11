package com.ruirua.sampleguideapp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ruirua.sampleguideapp.model.App;
import com.ruirua.sampleguideapp.model.User;

import java.util.List;

public class UserViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    public LiveData<List<User>> users;

    public UserViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is profile fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<List<User>> getUsers() {
        return users;
    }
}