package com.ruirua.sampleguideapp;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ruirua.sampleguideapp.model.Media;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.PointWith;
import com.ruirua.sampleguideapp.viewModel.PointViewModel;

import java.util.List;

public class MediaActivity extends AppCompatActivity {
    private TextView point_name;
    private ImageView media_imagem;
    private VideoView media_video;
    private ImageButton play;
    private ImageButton pause;
    private ImageButton stop;
    private TextView current_time;
    private SeekBar media_seekBar;
    private TextView total_time;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        point_name = findViewById(R.id.point_name);
        media_imagem = findViewById(R.id.point_image);
        media_video = findViewById(R.id.media_video);
        play = findViewById(R.id.point_start_button);
        pause = findViewById(R.id.point_pause_button);
        stop = findViewById(R.id.point_stop_button);
        current_time = findViewById(R.id.current_time);
        media_seekBar = findViewById(R.id.media_seekBar);
        total_time = findViewById(R.id.total_time);
        mediaPlayer = new MediaPlayer();

        media_seekBar.setMax(100);
        PointViewModel pvm = new ViewModelProvider(this).get(PointViewModel.class);
        Intent intent = getIntent();
        int point_id = intent.getIntExtra("point_id",0);
        pvm.setPointViewModel(point_id);
        LiveData<PointWith> point_data = pvm.getPointWith();
        point_data.observe(this,new_point->{
            if (new_point!=null){
                //List<Media> medias = new_point;
            }
        });
    }

}