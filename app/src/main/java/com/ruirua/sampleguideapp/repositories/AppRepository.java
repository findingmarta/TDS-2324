package com.ruirua.sampleguideapp.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.ruirua.sampleguideapp.BuildConfig;
import com.ruirua.sampleguideapp.DAOs.AppDAO;
import com.ruirua.sampleguideapp.database.GuideDatabase;
import com.ruirua.sampleguideapp.model.App;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppRepository {

    public AppDAO appDAO;
    public MediatorLiveData<List<App>> allApps;

    public AppRepository(Application application){
        GuideDatabase database = GuideDatabase.getInstance(application);
        appDAO = database.appDAO();
        allApps = new MediatorLiveData<>();
        allApps.addSource(
                appDAO.getApps(), localapps -> {
                    // TODO: ADD cache validation logic
                    if (localApps != null && !localApps.isEmpty()) {
                        allApps.setValue(localApps);
                    } else {
                        makeRequest();
                    }
                }
        );
    }

    public void insert(List<App> apps){
        new InsertAsyncTask(appDAO).execute(apps);
    }

    private void makeRequest() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BuildConfig.BRAGUIDE_BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AppAPI api = retrofit.create(AppAPI.class);
        Call<List<App>> call = api.getApps();
        call.enqueue(new retrofit2.Callback<List<App>>() {
            @Override
            public void onResponse(Call<List<App>> call, Response<List<App>> response) {
                if(response.isSuccessful()) {
                    insert(response.body());
                }
                else{
                    Log.e("main", "onFailure: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<App>> call, Throwable t) {
                Log.e("main", "onFailure: " + t.getMessage());
            }
        });
    }

    public LiveData<List<App>> getAllApps(){
        return allApps;
    }

    private static class InsertAsyncTask extends AsyncTask<List<App>,Void,Void> {
        private AppDAO appDAO;

        public InsertAsyncTask(AppDAO catDao) {
            this.appDAO=catDao;
        }

        @Override
        protected Void doInBackground(List<App>... lists) {
            appDAO.insert(lists[0]);
            return null;
        }
    }

}
