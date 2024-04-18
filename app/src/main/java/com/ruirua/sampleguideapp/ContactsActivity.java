package com.ruirua.sampleguideapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.ruirua.sampleguideapp.model.App;
import com.ruirua.sampleguideapp.model.AppWith;
import com.ruirua.sampleguideapp.viewModel.AppViewModel;

import java.util.List;

public class ContactsActivity extends GeneralActivity{


    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }
    @Override
    protected int getNavBarItemSelected () {
        return R.id.Home;
    }

    @Override
    protected void onGeneralActivityCreate() {



        setInfo();
    }

    public void setInfo(){
        AppViewModel avm = new ViewModelProvider(this).get(AppViewModel.class);
        LiveData<AppWith> appsData = avm.getApp();
        appsData.observe(this, appslist -> {
            if (appslist != null){

                AppWith app = appslist;
            }
        });
    }
}