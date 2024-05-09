package com.ruirua.sampleguideapp.repositories;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ruirua.sampleguideapp.BuildConfig;
import com.ruirua.sampleguideapp.database.GuideDatabase;
import com.ruirua.sampleguideapp.model.Edge;
import com.ruirua.sampleguideapp.model.Media;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.Prop_Point;
import com.ruirua.sampleguideapp.model.Prop_Trail;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.APIs.TrailAPI;
import com.ruirua.sampleguideapp.DAOs.TrailDAO;
import com.ruirua.sampleguideapp.model.TrailWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrailRepository {
    private final Application app;
    private final TrailDAO trailDAO;
    public MediatorLiveData<List<TrailWith>> allTrails;

    public TrailRepository(Application application){
        GuideDatabase database = GuideDatabase.getInstance(application);
        app = application;
        trailDAO = database.trailDAO();
        allTrails = new MediatorLiveData<>();
        allTrails.addSource(
                trailDAO.getTrailWith(), localTrails -> {
                    if (localTrails != null && !localTrails.isEmpty()) {
                        allTrails.setValue(localTrails);

                        // Fetch new data
                        try {
                            cacheValidation();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    } else {
                        try {
                            makeRequest();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
    }

    public void cacheValidation() throws IOException {
        // The date has an expiration time of 2 days
        long EXPIRATION_TIME = 2 * 24 * 60 * 60 * 1000;

        long last_update = getLastUpdate();
        long current_time = System.currentTimeMillis();

        long time_passed = current_time - last_update;
        if (time_passed >= EXPIRATION_TIME){
            makeRequest();
            Log.d("main", "FETCHED NEW DATA......");
            saveLastUpdate();
        }
    }

    public long getLastUpdate(){
        SharedPreferences sp = app.getSharedPreferences("BraGuia Shared Preferences", MODE_PRIVATE);
        return sp.getLong("last_update",0);
    }

    public void saveLastUpdate(){
        SharedPreferences sp = app.getSharedPreferences("BraGuia Shared Preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("last_update", System.currentTimeMillis());
        editor.apply();
    }

    public void insert(List<JsonElement> trails){
        new InsertAsyncTask(trailDAO).execute(trails);
    }

    private void makeRequest() throws IOException {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BuildConfig.BRAGUIDE_BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TrailAPI api = retrofit.create(TrailAPI.class);
        Call<List<JsonElement>> call = api.getTrails();
        call.enqueue(new retrofit2.Callback<List<JsonElement>>() {
            @Override
            public void onResponse(@NonNull Call<List<JsonElement>> call, @NonNull Response<List<JsonElement>> response) {
                if(response.isSuccessful()) {
                    insert(response.body());
                }
                else{
                    try {
                        assert response.errorBody() != null;
                        Log.e("main", "onFailure: "+response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<JsonElement>> call, @NonNull Throwable t) {
                Log.e("main", "onFailure: " + t.getMessage());
                Log.e("main", "message: "+ t.getCause());
            }
        });
    }

    private static class InsertAsyncTask extends AsyncTask<List<JsonElement>,Void,Void> {
        private final TrailDAO trailDAO;

        public InsertAsyncTask(TrailDAO catDao) {
            this.trailDAO=catDao;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<JsonElement>... lists) {
            List<Trail> trails = new ArrayList<>();
            List<Prop_Trail> props_trail = new ArrayList<>();
            List<Edge> edges = new ArrayList<>();

            for (JsonElement element : lists[0]){
                JsonObject trailObj = element.getAsJsonObject();

                // Trail
                Trail trail = new Trail(trailObj.get("id").getAsInt(), trailObj.get("trail_img").getAsString(),trailObj.get("trail_name").getAsString(),trailObj.get("trail_desc").getAsString(),trailObj.get("trail_duration").getAsInt(),trailObj.get("trail_difficulty").getAsString());
                trails.add(trail);

                // Properties
                for(JsonElement prop : trailObj.get("rel_trail").getAsJsonArray()){
                    JsonObject propObj = prop.getAsJsonObject();

                    Prop_Trail propTrail = new Prop_Trail(propObj.get("id").getAsInt(),propObj.get("value").getAsString(),propObj.get("attrib").getAsString(),propObj.get("trail").getAsInt());
                    props_trail.add(propTrail);
                }

                // Edges
                for(JsonElement ed : trailObj.get("edges").getAsJsonArray()){
                    JsonObject edgeObj = ed.getAsJsonObject();

                    // START POINT
                    JsonObject startObj = edgeObj.get("edge_start").getAsJsonObject();
                    int edge_start_id = setEdgePoint(startObj,trailDAO);

                    // END POINT
                    JsonObject endObj = edgeObj.get("edge_end").getAsJsonObject();
                    int edge_end_id = setEdgePoint(endObj,trailDAO);

                    // EDGE
                    Edge edge = new Edge(edgeObj.get("id").getAsInt(),edgeObj.get("edge_transport").getAsString(), edgeObj.get("edge_duration").getAsInt(),edgeObj.get("edge_desc").getAsString(),edgeObj.get("edge_trail").getAsInt(),edge_start_id,edge_end_id);
                    edges.add(edge);
                }
            }
            trailDAO.insertTrails(trails);
            trailDAO.insertPropsTrail(props_trail);
            trailDAO.insertEdges(edges);

            return null;
        }
    }


    // Return point's id
    public static int setEdgePoint(JsonObject pointObj, TrailDAO trailDAO){
        // Point
        Point edge_start = new Point(pointObj.get("id").getAsInt(),pointObj.get("pin_name").getAsString(),pointObj.get("pin_desc").getAsString(),pointObj.get("pin_lat").getAsFloat(),pointObj.get("pin_lng").getAsFloat(),pointObj.get("pin_alt").getAsFloat());
        trailDAO.insertPoint(edge_start);

        // Point's properties
        List<Prop_Point> props_point = new ArrayList<>();
        for(JsonElement prop : pointObj.get("rel_pin").getAsJsonArray()){
            JsonObject propObj = prop.getAsJsonObject();

            Prop_Point propPoint = new Prop_Point(propObj.get("id").getAsInt(),propObj.get("value").getAsString(),propObj.get("attrib").getAsString(),propObj.get("pin").getAsInt());
            props_point.add(propPoint);
        }
        trailDAO.insertPropsPoint(props_point);

        // Point's medias
        List<Media> medias = new ArrayList<>();
        for(JsonElement med : pointObj.get("media").getAsJsonArray()){
            JsonObject medObj = med.getAsJsonObject();

            Media media = new Media(medObj.get("id").getAsInt(),medObj.get("media_file").getAsString(),medObj.get("media_type").getAsString(),medObj.get("media_pin").getAsInt());
            medias.add(media);
        }
        trailDAO.insertMedia(medias);

        return edge_start.getPointId();
    }


    public LiveData<List<TrailWith>> getAllTrails(){
        return allTrails;
    }

    public LiveData<TrailWith> getTrailWithById(int id){
        return trailDAO.getTrailWithById(id);
    }

    public LiveData<List<Point>> getTrailPoints(int id){
        return trailDAO.getTrailPoints(id);
    }

}
