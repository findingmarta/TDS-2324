package com.ruirua.sampleguideapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.ruirua.sampleguideapp.model.App;
import com.ruirua.sampleguideapp.viewModel.AppViewModel;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setInfo();
    }

    public void setInfo(){
        /*AppViewModel avm = new ViewModelProvider(this).get(AppViewModel.class);
        LiveData<List<App>> appsData = avm.getApps();
        appsData.observe(this, appslist -> {
            if (!appslist.isEmpty()){
                App app = appslist.get(0);
            }
        });*/
    }
}