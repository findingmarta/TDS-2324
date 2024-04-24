package com.ruirua.sampleguideapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private SwitchCompat show_notifications;
    private TextView notification_distance_tag;
    private Spinner notification_distance;
    private String selected_distance;
    private SharedPreferences sp;
    //SharedPreferences.Editor editor;

    String distance;
    Boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        show_notifications = findViewById(R.id.show_notifications_button);
        notification_distance_tag = findViewById(R.id.distance_notification);
        notification_distance  = findViewById(R.id.distance_spinner);

        notification_distance_tag.setVisibility(View.GONE);
        notification_distance.setVisibility(View.GONE);

        sp = getApplication().getSharedPreferences("BraGuia Shared Preferences", MODE_PRIVATE);

        setNotifications();
        setDistance();
    }

    protected void setNotifications(){
        getNotificationsPreferences();
        show_notifications.setChecked(state);

        if (state){
            notification_distance.setVisibility(View.VISIBLE);
            notification_distance_tag.setVisibility(View.VISIBLE);
        } else {
            notification_distance.setVisibility(View.GONE);
            notification_distance_tag.setVisibility(View.GONE);
        }

        show_notifications.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked){
                notification_distance.setVisibility(View.VISIBLE);
                notification_distance_tag.setVisibility(View.VISIBLE);
            } else {
                notification_distance.setVisibility(View.GONE);
                notification_distance_tag.setVisibility(View.GONE);
            }

            // Save Settings
            state = isChecked;
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("notification_state", state);
            editor.apply();

        });
    }

    protected void setDistance(){
        List<String> distances = new ArrayList<>(Arrays.asList("10","30","50","70"));

        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                distances
        );

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner.
        notification_distance.setAdapter(adapter);

        notification_distance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // Get the selected value
                selected_distance = parent.getItemAtPosition(pos).toString();

                // Save Settings
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("notification_distance",selected_distance);
                editor.apply();

                distance = selected_distance;
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });

        getDistancePreferences();
        notification_distance.setSelection(adapter.getPosition(distance));
    }

    public void getNotificationsPreferences(){
        // For default the notification are on;
        state = sp.getBoolean("notification_state",true);
    }

    public void getDistancePreferences(){
        String selectedValueString= sp.getString("notification_distance",null);

        if (selectedValueString == null){
            // For default the distance is 50m;
            distance = "50";
        }
        else{
            distance = selectedValueString;
        }
    }
}