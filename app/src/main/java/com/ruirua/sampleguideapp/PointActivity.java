package com.ruirua.sampleguideapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.viewModel.PointViewModel;
import com.squareup.picasso.Picasso;

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

        // Get point's id
        Intent intent = getIntent();
        point_id = intent.getIntExtra("point_id",0);

        // Given the ID initialize the point and set its info
        pvm.setPointViewModel(point_id);
        LiveData<Point> pointData = pvm.getPoint();
        pointData.observe(this, new_point -> {
            if (new_point != null) {
                setPointInfo(new_point);
                setMedia(new_point);
                setVisited(new_point);
            }
        });
    }

    public void setPointInfo(Point point){
        point_name.setText(point.getPoint_name().toUpperCase());
        point_desc.setText(point.getPoint_desc());
        /*Picasso.get()
                .load(point.getPoint_image().replace("http:", "https:"))
                .into(point_image);*/

        // Initialy set the view's  related to the properties invisible
        point_prop.setVisibility(View.GONE);
        point_prop_tag.setVisibility(View.GONE);

        // If there's any property show them
    }

    public void setMedia (Point point) {
        //if (!isPremium) point_media_button.setVisibility(View.GONE);
        point_media_button.setVisibility(View.VISIBLE);
        point_media_button.setOnClickListener(view -> {
            Intent intent = new Intent(PointActivity.this, MediaActivity.class);
            intent.putExtra("pointID", point.getPointId());
            startActivity(intent);
        });
    }

    public void setVisited (Point point) {
        //if (!isPremium) point_media_button.setVisibility(View.GONE);
        point_visited_button.setVisibility(View.VISIBLE);
        point_visited_button.setOnClickListener(view -> {
            // Add point to the user's history
            Toast.makeText(PointActivity.this, "Point of Interest added to your history!", Toast.LENGTH_SHORT).show();
        });
    }
}