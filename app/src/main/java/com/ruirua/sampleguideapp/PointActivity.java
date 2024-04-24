package com.ruirua.sampleguideapp;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.ruirua.sampleguideapp.model.History_Point;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.PointWith;
import com.ruirua.sampleguideapp.model.Prop_Point;
import com.ruirua.sampleguideapp.viewModel.HistoryViewModel;
import com.ruirua.sampleguideapp.viewModel.PointViewModel;

import java.util.List;
import java.util.Locale;

public class PointActivity extends AppCompatActivity {
    private int point_id;
    private TextView point_name;
    private ImageView point_image;
    private ImageButton point_media_button;
    private Button point_location_button;
    private TextView point_desc;
    private TextView point_prop;
    private TextView point_prop_tag;
    private Button point_visited_button;
    private Boolean isPremium;

    Point point;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);

        // Bind points's views
        point_name = findViewById(R.id.point_name);
        point_image = findViewById(R.id.point_image);
        point_media_button = findViewById(R.id.point_media_button);
        point_location_button = findViewById(R.id.point_location_button);
        point_desc = findViewById(R.id.point_desc);
        point_prop = findViewById(R.id.point_prop);
        point_prop_tag = findViewById(R.id.point_prop_tag);
        point_visited_button = findViewById(R.id.point_visited_button);

        // Get the info from de database
        // Trail
        PointViewModel pvm = new ViewModelProvider(this).get(PointViewModel.class);

        // Get user's type
        isPremium = pvm.getPremium();

        // Get point's id
        Intent intent = getIntent();
        point_id = intent.getIntExtra("point_id",0);

        // Given the ID initialize the point and set its info
        pvm.setPointViewModel(point_id);
        LiveData<PointWith> pointData = pvm.getPointWith();
        pointData.observe(this, new_point -> {
            if (new_point != null) {
                point = new_point.getPoint();

                setPointInfo();
                setProperties(new_point.getProp_point());
                setMedia();
                setLocation();
            }
        });

        HistoryViewModel hvm = new ViewModelProvider(this).get(HistoryViewModel.class);
        // Check if point is already in the history
        LiveData<History_Point> historyPointData = hvm.checkHistoryPoint(point_id);
        historyPointData.observe(this, new_history_point -> {
            if (new_history_point == null){
                // Set visited if point not in the history
                setVisited(hvm);
            } else{
                // Block the "Mark As Visited" button
                point_visited_button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_grey)));
                point_visited_button.setClickable(false);
            }
        });
    }

    public void setPointInfo(){
        point_name.setText(point.getPoint_name().toUpperCase(Locale.getDefault()));
        point_desc.setText(point.getPoint_desc());
        /*Picasso.get()
                .load(point.getPoint_image().replace("http:", "https:"))
                .into(point_image);*/
    }

    public void setProperties(List<Prop_Point> props){
        if (!props.isEmpty()){
            // With Spanned I can customize text
            StringBuilder sb = new StringBuilder("<ul>");

            // Put properties in a bullet list format
            for(Prop_Point p : props) {
                sb.append("<li><b>").append("  ").append(p.getAttrib()).append(":</b>");
                sb.append(" ").append(p.getValue()).append("</li>");
            }
            sb.append("</ul>");
            Spanned spannedExtras = HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY);
            point_prop.setText(spannedExtras);
        } else {
            point_prop.setVisibility(View.GONE);
            point_prop_tag.setVisibility(View.GONE);
        }
    }

    public void setMedia () {
        if (!isPremium) {
            point_media_button.setVisibility(View.GONE);
        }
        else {
            point_media_button.setOnClickListener(view -> {
                Intent intent = new Intent(PointActivity.this, MediaActivity.class);
                intent.putExtra("point_id", point.getPointId());
                startActivity(intent);
            });
        }
    }

    public void setVisited (HistoryViewModel hvm) {
        if (!isPremium) {
            point_visited_button.setVisibility(View.GONE);
        }
        else {
            point_visited_button.setOnClickListener(view -> {
                // Add point to the user's history
                hvm.insertHistoryPoint(point.getPointId());
                Toast.makeText(PointActivity.this, "Point of Interest added to your history!", Toast.LENGTH_SHORT).show();
            });
        }
    }

    public void setLocation(){
        point_location_button.setOnClickListener(view -> {
            float lat = point.getPoint_lat();
            float lng = point.getPoint_lng();
            // Adding the location's name shows a pin on its coordinates
            String location = point.getPoint_name();

            Uri location_URI =  Uri.parse("geo:" + lat + "," + lng + "?q=" + location);

            Intent intent = new Intent(Intent.ACTION_VIEW, location_URI);
            // Set Google Maps as the application responsible to open this intent
            intent.setPackage("com.google.android.apps.maps");

            // If Google Maps is available start the activity
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        });
    }
}