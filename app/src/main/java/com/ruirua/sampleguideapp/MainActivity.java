package com.ruirua.sampleguideapp;

import android.os.Bundle;

public class MainActivity extends GeneralActivity{
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
    }
}