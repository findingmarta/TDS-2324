package com.ruirua.sampleguideapp.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.room.RoomDatabase;

import com.ruirua.sampleguideapp.BuildConfig;
import com.ruirua.sampleguideapp.model.GuideDatabase;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.model.TrailAPI;
import com.ruirua.sampleguideapp.model.TrailDAO;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrailRepository {

    public TrailDAO trailDAO;
    public MediatorLiveData<List<Trail>> allTrails;

    public TrailRepository(Application application){
        GuideDatabase database = GuideDatabase.getInstance(application);
        trailDAO = database.trailDAO();
        allTrails = new MediatorLiveData<>();
        allTrails.addSource(
                trailDAO.getTrails(), localTrails -> {
                    // TODO: ADD cache validation logic
                    if (localTrails != null && localTrails.size() > 0) {
                        allTrails.setValue(localTrails);
                    } else {
                        makeRequest();
                    }
                }
        );
    }

    public void insert(List<Trail> trails){
        new InsertAsyncTask(trailDAO).execute(trails);
    }

    private void makeRequest() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BuildConfig.BRAGUIDE_BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TrailAPI api = retrofit.create(TrailAPI.class);
        Call<List<Trail>> call = api.getTrails();
        call.enqueue(new retrofit2.Callback<List<Trail>>() {
            @Override
            public void onResponse(Call<List<Trail>> call, Response<List<Trail>> response) {
                if(response.isSuccessful()) {
                    insert(response.body());
                }
                else{
                    Log.e("main", "onFailure: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Trail>> call, Throwable t) {
                Log.e("main", "onFailure: " + t.getMessage());
            }
        });
    }

    public LiveData<List<Trail>> getAllTrails(){
        return allTrails;
    }

    private static class InsertAsyncTask extends AsyncTask<List<Trail>,Void,Void> {
        private TrailDAO trailDAO;

        public InsertAsyncTask(TrailDAO catDao) {
            this.trailDAO=catDao;
        }

        @Override
        protected Void doInBackground(List<Trail>... lists) {
            trailDAO.insert(lists[0]);
            return null;
        }
    }

}
