package com.ruirua.sampleguideapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public abstract class GeneralActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    protected BottomNavigationView bottomNav;
    protected abstract int getContentViewId();
    protected abstract int getNavBarItemSelected();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(this);
        bottomNav.setSelectedItemId(getNavBarItemSelected());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.Profile):
                if (getContentViewId() != R.layout.activity_profile){
                    Intent profileIntent = new Intent(this, ProfileActivity.class);
                    startActivity(profileIntent);
                }
                break;

            case (R.id.Home):
                if (getContentViewId() != R.layout.activity_main) {
                    Intent homeIntent = new Intent(this, MainActivity.class);
                    startActivity(homeIntent);
                }
                break;

            case (R.id.Trails):
                if (getContentViewId() != R.layout.activity_trails) {
                    Intent trailsIntent = new Intent(this, TrailsActivity.class);
                    startActivity(trailsIntent);
                }
                break;

            case (R.id.SOS):
                break;                        // TODO Acabar este Menu

            case (R.id.Logout):
                break;
            default:
                return false;
        }
        return true;
    }
}