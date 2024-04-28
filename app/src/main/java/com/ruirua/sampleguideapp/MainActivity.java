package com.ruirua.sampleguideapp;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ruirua.sampleguideapp.adapters.PartnersRecyclerViewAdapter;
import com.ruirua.sampleguideapp.model.App;
import com.ruirua.sampleguideapp.model.AppWith;
import com.ruirua.sampleguideapp.model.Partner;
import com.ruirua.sampleguideapp.model.Social;
import com.ruirua.sampleguideapp.model.User;
import com.ruirua.sampleguideapp.viewModel.AppViewModel;
import com.ruirua.sampleguideapp.viewModel.UserViewModel;

import java.util.List;

public class MainActivity extends GeneralActivity{
    private TextView mapsWarning;
    private TextView appDesc;
    private TextView appLandingPage;
    private FloatingActionButton socials_button;
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

        mapsWarning = findViewById(R.id.home_mapsWarningText);
        appLandingPage = findViewById(R.id.home_appLandingPage);
        appDesc = findViewById(R.id.home_appDesc);
        socials_button = findViewById(R.id.home_socials_button);

        setAppInfo();
        createNotificationChannel();
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

        setMapsLink();
        saveUserType();
    }

    public void setMapsLink(){
        String fullText = mapsWarning.getText().toString();

        // Index of the text "Google Maps installed" within the full text
        int startIndex = fullText.indexOf("Google Maps");
        int endIndex = startIndex + "Google Maps".length();

        Spannable spannable = new SpannableString(fullText);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                try {
                    // Attempt to open Google Maps in Play Store
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=com.google.android.apps.maps"));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // If Play Store is not available, open the Google Play Store website in browser
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps"));
                    startActivity(intent);
                }
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true); // Make the clickable text underlined
            }
        };

        // Set the clickable span only on the portion of text "Google Maps"
        spannable.setSpan(clickableSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mapsWarning.setText(spannable);
        // Make link clickable
        mapsWarning.setMovementMethod(LinkMovementMethod.getInstance());
        // Remove link highlight
        mapsWarning.setHighlightColor(Color.TRANSPARENT);
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
            PartnersRecyclerViewAdapter adapter = new PartnersRecyclerViewAdapter(partners);
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

    @SuppressLint("ObsoleteSdkInt")
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("Notification Service", "Created a notification channel");
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyChannel", "Notification Channel", importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}