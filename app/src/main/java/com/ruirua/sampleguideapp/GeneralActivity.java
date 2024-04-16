package com.ruirua.sampleguideapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    SharedPreferences sp;
    public static final String name = "Braguia Shared Preferences";
    public static final String key = "cookies";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        sp = getSharedPreferences(name, Context.MODE_PRIVATE);

        // User is logged in
        if (sp.getString(key, null) != null) {

            bottomNav = findViewById(R.id.bottom_navigation);
            bottomNav.setOnNavigationItemSelectedListener(this);
            bottomNav.setSelectedItemId(getNavBarItemSelected());
        } else {
            startLoginActivity();
        }
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
                if (getContentViewId() != R.layout.activity_contacts) {
                    Intent contactsIntent = new Intent(this, ContactsActivity.class);
                    startActivity(contactsIntent);
                }
                break;

            case (R.id.Logout):         // TODO Acabar este Menu
                break;
            default:
                return false;
        }
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();

        /*// Verifica se o user está logged in ou não
        sp = getSharedPreferences("Braguia Shared Preferences",MODE_PRIVATE);
        if (sp.getString("cookies", null) != null) {
            bottomNav.setSelectedItemId(getNavBarItemSelected());
        } else{
            // Start login Activity
            startLoginActivity();
        }*/
    }

    public void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}