package com.ruirua.sampleguideapp;


import android.os.Bundle;

public class ProfileActivity extends GeneralActivity {
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
    }
}