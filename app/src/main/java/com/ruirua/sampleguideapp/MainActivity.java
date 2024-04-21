package com.ruirua.sampleguideapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ruirua.sampleguideapp.adapters.PartnersRecyclerViewAdapter;
import com.ruirua.sampleguideapp.adapters.TrailsRecyclerViewAdapter;
import com.ruirua.sampleguideapp.model.App;
import com.ruirua.sampleguideapp.model.AppWith;
import com.ruirua.sampleguideapp.model.Partner;
import com.ruirua.sampleguideapp.model.Social;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.model.User;
import com.ruirua.sampleguideapp.viewModel.AppViewModel;
import com.ruirua.sampleguideapp.viewModel.UserViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends GeneralActivity{
    //private TextView mapsWarning;                // TODO Ver como fazer este warning
    private TextView appDesc;
    private TextView appLandingPage;
    private FloatingActionButton socials_button;
    private PartnersRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private boolean isPremium = false;


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
        // set up the RecyclerView
        recyclerView = findViewById(R.id.rv_partners);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        appLandingPage = findViewById(R.id.home_appLandingPage);
        appDesc = findViewById(R.id.home_appDesc);
        socials_button = findViewById(R.id.home_socials_button);

        setAppInfo();
    }

    public void setAppInfo(){
        AppViewModel avm = new ViewModelProvider(this).get(AppViewModel.class);
        LiveData<AppWith> appsData = avm.getApp();
        appsData.observe(this, appWith -> {
            if (appWith != null){
                App app = appWith.getApp();

                appLandingPage.setText(app.getApp_landing_page_text());
                appDesc.setText(app.getApp_desc());

                setPartners(appWith);
                setSocials(appWith);
            }
        });

        saveUserType();
    }

    public void setSocials(AppWith appWith){
        // Get Social from App
        List<Social> socials = appWith.getSocials();
        if (!socials.isEmpty()) {
            socials_button.setOnClickListener(view -> {
                Social social = socials.get(0);
                String url = social.getSocial_url();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            });
        }
    }

    public void setPartners(AppWith appWith){
        // Get Partners from App
        List<Partner> partners = appWith.getPartners();
        if (!partners.isEmpty()){
            adapter = new PartnersRecyclerViewAdapter(partners);
            recyclerView.setAdapter(adapter);
        }
    }

    public void saveUserType(){
        UserViewModel uvm = new ViewModelProvider(this).get(UserViewModel.class);
        LiveData<List<User>> usersData = uvm.getUsers();
        usersData.observe(this, users -> {
            User user = users.get(0);
            isPremium = user.getUser_type().equals("Premium");

            // Save user's type on Shared Preferences
            SharedPreferences sp = getApplication().getSharedPreferences("BraGuia Shared Preferences",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("user_type",isPremium);
            editor.apply();
        });
    }
}