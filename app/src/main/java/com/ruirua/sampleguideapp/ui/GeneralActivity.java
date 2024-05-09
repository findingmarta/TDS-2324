package com.ruirua.sampleguideapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ruirua.sampleguideapp.R;
import com.ruirua.sampleguideapp.viewModel.UserViewModel;


public abstract class GeneralActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {
    protected BottomNavigationView bottomNav;
    protected abstract int getContentViewId();
    protected abstract int getNavBarItemSelected();

    // Method used to extend the method onCreate
    protected abstract void onGeneralActivityCreate();


    SharedPreferences sp;
    public static final String name = "BraGuia Shared Preferences";
    public static final String key = "cookies";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get preferences
        sp = getSharedPreferences(name, MODE_PRIVATE);

        // User is logged in
        if (sp.getString(key,null) != null) {
            setContentView(getContentViewId());

            bottomNav = findViewById(R.id.bottom_navigation);
            bottomNav.setOnItemSelectedListener(this);
            bottomNav.setSelectedItemId(getNavBarItemSelected());

            onGeneralActivityCreate();
        } else{
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

            case (R.id.Logout):
                UserViewModel uvm = new ViewModelProvider(this).get(UserViewModel.class);
                uvm.logout();

                // Start Login Activity
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                return false;
        }
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Verifica se o user está logged in ou não
        sp = getSharedPreferences(name,MODE_PRIVATE);
        if (sp.getString(key, null) != null) {
            bottomNav.setSelectedItemId(getNavBarItemSelected());
        } else{
            startLoginActivity();
        }
    }

    public void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}