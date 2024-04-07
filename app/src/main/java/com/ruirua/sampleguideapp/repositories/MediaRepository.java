package com.ruirua.sampleguideapp.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.ruirua.sampleguideapp.BuildConfig;
import com.ruirua.sampleguideapp.DAOs.MediaDAO;
import com.ruirua.sampleguideapp.database.GuideDatabase;
import com.ruirua.sampleguideapp.model.Media;
import com.ruirua.sampleguideapp.model.MediaAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MediaRepository {

    public MediaDAO mediaDAO;
    public MediatorLiveData<List<Media>> allMedias;

    public MediaRepository(Application application){
        GuideDatabase database = GuideDatabase.getInstance(application);
        mediaDAO = database.mediaDAO();
        allMedias = new MediatorLiveData<>();
        allMedias.addSource(
                mediaDAO.getMedias(), localMedias -> {
                    // TODO: ADD cache validation logic
                    if (localMedias != null && !localMedias.isEmpty()) {
                        allMedias.setValue(localMedias);
                    } else {
                        makeRequest();
                    }
                }
        );
    }

    public void insert(List<Media> medias){
        new InsertAsyncTask(mediaDAO).execute(medias);
    }

    private void makeRequest() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BuildConfig.BRAGUIDE_BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MediaAPI api = retrofit.create(MediaAPI.class);
        Call<List<Media>> call = api.getMedias();
        call.enqueue(new retrofit2.Callback<List<Media>>() {
            @Override
            public void onResponse(Call<List<Media>> call, Response<List<Media>> response) {
                if(response.isSuccessful()) {
                    insert(response.body());
                }
                else{
                    Log.e("main", "onFailure: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Media>> call, Throwable t) {
                Log.e("main", "onFailure: " + t.getMessage());
            }
        });
    }

    public LiveData<List<Media>> getAllMedias(){
        return allMedias;
    }

    private static class InsertAsyncTask extends AsyncTask<List<Media>,Void,Void> {
        private MediaDAO mediaDAO;

        public InsertAsyncTask(MediaDAO catDao) {
            this.mediaDAO=catDao;
        }

        @Override
        protected Void doInBackground(List<Media>... lists) {
            mediaDAO.insert(lists[0]);
            return null;
        }
    }

}
