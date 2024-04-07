package com.ruirua.sampleguideapp.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.ruirua.sampleguideapp.BuildConfig;
import com.ruirua.sampleguideapp.DAOs.PointDAO;
import com.ruirua.sampleguideapp.database.GuideDatabase;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.PointAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PointRepository {

    public PointDAO pointDAO;
    public MediatorLiveData<List<Point>> allPoints;

    public PointRepository(Application application){
        GuideDatabase database = GuideDatabase.getInstance(application);
        pointDAO = database.pointDAO();
        allPoints = new MediatorLiveData<>();
        allPoints.addSource(
                pointDAO.getPoints(), localPoints -> {
                    // TODO: ADD cache validation logic
                    if (localPoints != null && !localPoints.isEmpty()) {
                        allPoints.setValue(localPoints);
                    } else {
                        makeRequest();
                    }
                }
        );
    }

    public void insert(List<Point> points){
        new InsertAsyncTask(pointDAO).execute(points);
    }

    private void makeRequest() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BuildConfig.BRAGUIDE_BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PointAPI api = retrofit.create(PointAPI.class);
        Call<List<Point>> call = api.getPoints();
        call.enqueue(new retrofit2.Callback<List<Point>>() {
            @Override
            public void onResponse(Call<List<Point>> call, Response<List<Point>> response) {
                if(response.isSuccessful()) {
                    insert(response.body());
                }
                else{
                    Log.e("main", "onFailure: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Point>> call, Throwable t) {
                Log.e("main", "onFailure: " + t.getMessage());
            }
        });
    }

    public LiveData<List<Point>> getAllPoints(){
        return allPoints;
    }

    private static class InsertAsyncTask extends AsyncTask<List<Point>,Void,Void> {
        private PointDAO pointDAO;

        public InsertAsyncTask(PointDAO catDao) {
            this.pointDAO=catDao;
        }

        @Override
        protected Void doInBackground(List<Point>... lists) {
            pointDAO.insert(lists[0]);
            return null;
        }
    }

}
