package com.ruirua.sampleguideapp.ui;


import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
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
import androidx.lifecycle.ViewModelProvider;

import com.ruirua.sampleguideapp.R;
import com.ruirua.sampleguideapp.model.Media;
import com.ruirua.sampleguideapp.model.PointWith;
import com.ruirua.sampleguideapp.viewModel.PointViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class MediaActivity extends AppCompatActivity {
    private List<Media> medias;
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
    private Button download;
    private TextView no_media;
    private boolean isConnected = false;
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
        download = findViewById(R.id.point_download_button);
        no_media = findViewById(R.id.no_media);

        mediaPlayer = new MediaPlayer();

        // Set the view invisible
        setEverythingInvisible();

        // Check internet connection
        if(isConnected()){
            isConnected = true;
        }

        // Get the points from the database
        PointViewModel pvm = new ViewModelProvider(this).get(PointViewModel.class);
        Intent intent = getIntent();
        int point_id = intent.getIntExtra("point_id",0);
        pvm.setPointViewModel(point_id);

        LiveData<PointWith> point_data = pvm.getPointWith();
        point_data.observe(this,new_point->{
            if (new_point!=null){
                // Set point's name
                point_name.setText(new_point.getPoint().getPoint_name());
                medias = new_point.getMedias();

                if (medias.isEmpty()){
                    no_media.setVisibility(View.VISIBLE);
                } else {
                    for (Media media : medias) {
                        // Get the media's file name
                        String url = media.getMedia_file().replace("http:", "https:");
                        int lastIndex = url.lastIndexOf('/');
                        String filename = url.substring(lastIndex + 1);

                        // Audio
                        if (media.getMedia_type().equals("R")) {
                            prepareMediaPlayer(media, filename);
                        }
                        // Video
                        if (media.getMedia_type().equals("V")) {
                            String videoUrl = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/" + filename;

                            File file = new File(videoUrl);
                            // If the file isn't stored locally an if i have an internet connection
                            if (!file.exists()) {
                                if (isConnected){
                                    media_video.setVisibility(View.VISIBLE);
                                    download.setVisibility(View.VISIBLE);
                                    videoUrl = media.getMedia_file().replace("http:", "https:");
                                    Uri uri = Uri.parse(videoUrl);
                                    media_video.setVideoURI(uri);
                                    media_video.start();
                                } else {
                                    no_media.setText(R.string.media_no_internet);
                                    no_media.setVisibility(View.VISIBLE);
                                }
                            } else {
                                media_video.setVisibility(View.VISIBLE);
                                Uri uri = Uri.parse(videoUrl);
                                media_video.setVideoURI(uri);
                                media_video.start();
                            }
                        }
                        // Image
                        if (media.getMedia_type().equals("I")) {
                            String imageUrl = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/" + filename;

                            File file = new File(imageUrl);
                            // If the file isn't stored locally an if i have an internet connection
                            if (!file.exists()) {
                                if (isConnected){
                                    media_imagem.setVisibility(View.VISIBLE);
                                    download.setVisibility(View.VISIBLE);
                                    Picasso.get()
                                            .load(media.getMedia_file().replace("http:", "https:"))
                                            .into(media_imagem);
                                } else {
                                    no_media.setText(R.string.media_no_internet);
                                    no_media.setVisibility(View.VISIBLE);
                                }
                            } else {
                                media_imagem.setVisibility(View.VISIBLE);
                                Picasso.get()
                                        .load(file)
                                        .into(media_imagem);
                            }
                        }
                    }
                }
            }
        });

        download.setOnClickListener(view -> downloadAllMedia());
    }



    private void prepareMediaPlayer(Media media,String filename){
        try{
            mediaPlayer.setAudioAttributes(
                    new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
            );
            String filePath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/" + filename;

            File file = new File(filePath);
            if (!file.exists()) {
                if (isConnected){
                    setAudioVisible();
                    download.setVisibility(View.VISIBLE);
                    mediaPlayer.setDataSource(media.getMedia_file().replace("http:", "https:"));
                    mediaPlayer.prepare();
                    total_time.setText(milisecondsToTimer(mediaPlayer.getDuration()));

                    setMusicButtons(media, filename);
                    media_seekBar.setMax(mediaPlayer.getDuration());
                    controlSeekBar();
                } else {
                    no_media.setText(R.string.media_no_internet);
                    no_media.setVisibility(View.VISIBLE);
                }
            }else{
                setAudioVisible();
                mediaPlayer.setDataSource(filePath);
                mediaPlayer.prepare();
                total_time.setText(milisecondsToTimer(mediaPlayer.getDuration()));

                setMusicButtons(media, filename);
                media_seekBar.setMax(mediaPlayer.getDuration());
                controlSeekBar();
            }
        } catch (Exception exception){
            Log.e("media", Objects.requireNonNull(exception.getMessage()));
        }
    }


    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            media_seekBar.setProgress(mediaPlayer.getCurrentPosition());
            handler.postDelayed(updater,1000);
        }
    };


    private void setMusicButtons(Media media,String filename){
        play.setOnClickListener(view -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                handler.postDelayed(updater, 0);
            }
        });

        pause.setOnClickListener(view -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                handler.removeCallbacks(updater);
            }
        });

        stop.setOnClickListener(view -> {
            if (mediaPlayer.getCurrentPosition() > 0) {
                mediaPlayer.stop();
                mediaPlayer.reset(); // Redefine o estado do MediaPlayer
                prepareMediaPlayer(media, filename);
                media_seekBar.setProgress(0);
                current_time.setText(milisecondsToTimer(0));
            }
        });
    }


    private void controlSeekBar(){
        media_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                }
                current_time.setText(milisecondsToTimer(mediaPlayer.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

        });
        mediaPlayer.setOnCompletionListener(mediaPlayer -> mediaPlayer.seekTo(0));
    }


    public void downloadAllMedia(){
        Toast.makeText(this, "Downloading media...", Toast.LENGTH_SHORT).show();
        for(Media media : medias){
            String url = media.getMedia_file().replace("http:", "https:");
            int lastIndex = url.lastIndexOf('/');
            String filename = url.substring(lastIndex + 1);

            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

            request.setTitle(filename)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalFilesDir(getApplication(),Environment.DIRECTORY_DOWNLOADS, filename);

            manager.enqueue(request);
        }
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
        download.setVisibility(View.GONE);
        no_media.setVisibility(View.GONE);
    }

    private void setAudioVisible(){
        play.setVisibility(View.VISIBLE);
        pause.setVisibility(View.VISIBLE);
        stop.setVisibility(View.VISIBLE);
        current_time.setVisibility(View.VISIBLE);
        media_seekBar.setVisibility(View.VISIBLE);
        total_time.setVisibility(View.VISIBLE);
    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.release();
        handler.removeCallbacks(updater);
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        mediaPlayer.release();
    }

}