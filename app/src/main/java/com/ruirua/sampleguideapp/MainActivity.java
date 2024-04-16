package com.ruirua.sampleguideapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ruirua.sampleguideapp.model.App;
import com.ruirua.sampleguideapp.model.AppWith;
import com.ruirua.sampleguideapp.model.Partner;
import com.ruirua.sampleguideapp.model.Social;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.viewModel.AppViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends GeneralActivity{
    //private TextView mapsWarning;                // TODO Ver como fazer este warning
    private TextView appDesc;
    private TextView appLandingPage;
    private TextView partnerName;
    private TextView partnerPhone;
    private TextView partnerUrl;
    private TextView partnerEmail;
    private TextView partnerDesc;
    private FloatingActionButton socials_button;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }
    @Override
    protected int getNavBarItemSelected () {
        return R.id.Home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appLandingPage = findViewById(R.id.home_appLandingPage);
        appDesc = findViewById(R.id.home_appDesc);
        partnerName = findViewById(R.id.partner_name);
        partnerPhone = findViewById(R.id.partner_phone);
        partnerUrl = findViewById(R.id.partner_url);
        partnerEmail = findViewById(R.id.partner_email);
        partnerDesc = findViewById(R.id.partner_desc);
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
    }

    public void setSocials(AppWith appWith){
        // Get Social from App
        List<Social> socials = appWith.getSocials();
        if (!socials.isEmpty()){
            Social social = socials.get(0);
            String url = "";
        }

        socials_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                //startActivity(intent);
            }
        });
    }

    public void setPartners(AppWith appWith){
        // Get Partners from App
        List<Partner> partners = appWith.getPartners();
        if (!partners.isEmpty()){
            Partner partner = partners.get(0);

            partnerName.setText(partner.getPartner_name());
            partnerPhone.setText(partner.getPartner_phone());
            partnerEmail.setText(partner.getPartner_mail());
            partnerUrl.setText(partner.getPartner_url());
            partnerDesc.setText(partner.getPartner_desc());
        }
    }
}