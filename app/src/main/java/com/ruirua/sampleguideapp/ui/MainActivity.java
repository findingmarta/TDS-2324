package com.ruirua.sampleguideapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.widget.Toast;


import com.ruirua.sampleguideapp.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // as soon as the application opens the first
        // fragment should be shown to the user
        // in this case it is algorithm fragment
        //getSupportFragmentManager().beginTransaction().replace(R.id.navbar_fragment_container, new MainActivity()).commit();
    }



    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        switch (item.getItemId()) {
            case R.id.SOS:
                Toast.makeText(this, "Emergency Contacts", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Home:
                Toast.makeText(this, "Homepage", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Trails:
                Toast.makeText(this, "Trails List", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Profile:
                Toast.makeText(this, "User's profile", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Logout:
                Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    };


    @Override
    protected void onPause() {
        super.onPause();
    }
}