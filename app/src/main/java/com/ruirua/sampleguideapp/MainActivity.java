package com.ruirua.sampleguideapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.ruirua.sampleguideapp.model.App;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.viewModel.AppViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends GeneralActivity{
    private TextView appLandingPage;
    private TextView appDesc;
    //private TextView mapsWarning;                // TODO Ver como fazer este warning

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }
    protected int getNavBarItemSelected () {
        return R.id.Home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appLandingPage = findViewById(R.id.home_appLandingPage);
        appDesc = findViewById(R.id.home_appDesc);

        setInfo();
    }

    public void setInfo(){
        AppViewModel avm = new ViewModelProvider(this).get(AppViewModel.class);
        LiveData<List<App>> appsData = avm.getApps();
        appsData.observe(this, pointslist -> {
            App app = pointslist.get(0);
            appLandingPage.setText(app.getApp_landing_page_text());
            appDesc.setText(app.getApp_desc());
        });
    }
}