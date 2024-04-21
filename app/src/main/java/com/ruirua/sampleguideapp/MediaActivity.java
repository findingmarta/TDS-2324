package com.ruirua.sampleguideapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
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
        setEverythingInvisible();
        PointViewModel pvm = new ViewModelProvider(this).get(PointViewModel.class);
        Intent intent = getIntent();
        int point_id = intent.getIntExtra("point_id",0);
        pvm.setPointViewModel(point_id);
        LiveData<PointWith> point_data = pvm.getPointWith();
        point_data.observe(this,new_point->{
            if (new_point!=null){
                List<Media> medias = new_point.getMedias();
                for(Media media : medias){
                    if (media.getMedia_type().equals("R")){
                        setAudioVisible();
                        prepareMediaPlayer(media);
                        setMusicButtons(media);
                        controlSeekBar();
                    }

                }
            }
        });
    }

    private void prepareMediaPlayer(Media media){
        try{
            mediaPlayer.setDataSource(media.getMedia_file());
            mediaPlayer.prepare();
            total_time.setText(milisecondsToTimer(mediaPlayer.getDuration()));

        } catch (Exception exception){
            Toast.makeText(this,exception.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            current_time.setText(milisecondsToTimer(currentDuration));
        }
    };

    private void updateSeekBar(){
        if (mediaPlayer.isPlaying()){
            media_seekBar.setProgress((int) (((float)mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration())*100));
        }
    }

    private void setMusicButtons(Media media){
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                updateSeekBar();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(updater);
                mediaPlayer.pause();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(updater);
                mediaPlayer.stop();
                current_time.setText(mediaPlayer.getCurrentPosition());
                prepareMediaPlayer(media);
            }
        });
    }



    @SuppressLint("ClickableViewAccessibility")
    private void controlSeekBar(){
        media_seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SeekBar seekBar = (SeekBar) view;
                int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                current_time.setText(milisecondsToTimer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });
    }

    private String milisecondsToTimer(long milliseconds){
        String timer = "";
        String minutes_part = "";
        String seconds_part= "";
        // Calculate hours, minutes, and seconds
        int hours = (int) milliseconds / (1000 * 60 * 60);
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds =(int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60)) / 1000;
        if (hours>0){
            timer = hours + ":";
        }
        if (minutes<10){
            minutes_part = "0" + minutes + ":";
        } else {
            minutes_part = minutes + ":";
        }
        if (seconds<10) {
            seconds_part = "0" + seconds;
        }else {
            seconds_part = seconds_part + seconds;
        }
        timer = timer + minutes_part + seconds_part;

        return timer;
    }
    private void setEverythingInvisible(){
        media_imagem.setVisibility(View.GONE);
        media_video.setVisibility(View.GONE);
        play.setVisibility(View.GONE);
        pause.setVisibility(View.GONE);
        stop.setVisibility(View.GONE);
        current_time.setVisibility(View.GONE);
        media_seekBar.setVisibility(View.GONE);
        total_time.setVisibility(View.GONE);
    }
    private void setAudioVisible(){
        play.setVisibility(View.VISIBLE);
        pause.setVisibility(View.VISIBLE);
        stop.setVisibility(View.VISIBLE);
        current_time.setVisibility(View.VISIBLE);
        media_seekBar.setVisibility(View.VISIBLE);
        total_time.setVisibility(View.VISIBLE);
    }
}