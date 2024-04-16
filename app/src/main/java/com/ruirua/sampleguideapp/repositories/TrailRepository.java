package com.ruirua.sampleguideapp.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ruirua.sampleguideapp.BuildConfig;
import com.ruirua.sampleguideapp.database.GuideDatabase;
import com.ruirua.sampleguideapp.model.App;
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

    public TrailDAO trailDAO;
    public MediatorLiveData<List<TrailWith>> allTrails;

    public TrailRepository(Application application){
        GuideDatabase database = GuideDatabase.getInstance(application);
        trailDAO = database.trailDAO();
        allTrails = new MediatorLiveData<>();
        allTrails.addSource(
                trailDAO.getTrailWith(), localTrails -> {
                    // TODO: ADD cache validation logic
                    if (localTrails != null && !localTrails.isEmpty()) {
                        allTrails.setValue(localTrails);
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
            public void onResponse(Call<List<JsonElement>> call, Response<List<JsonElement>> response) {
                if(response.isSuccessful()) {
                    insert(response.body());
                }
                else{
                    Log.e("main", "onFailure: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<JsonElement>> call, Throwable t) {
                Log.e("main", "onFailure: " + t.getMessage());
                Log.e("main", "message: "+ t.getCause());
            }
        });
    }

    private static class InsertAsyncTask extends AsyncTask<List<JsonElement>,Void,Void> {
        private TrailDAO trailDAO;

        public InsertAsyncTask(TrailDAO catDao) {
            this.trailDAO=catDao;
        }

        @Override
        protected Void doInBackground(List<JsonElement>... lists) {
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
                    JsonObject endObj = edgeObj.get("edge_start").getAsJsonObject();
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

    public LiveData<Trail> getTrailById(int id){
        return trailDAO.getTrailById(id);
    }

    public LiveData<TrailWith> getTrailWithById(int id){
        return trailDAO.getTrailWithById(id);
    }

    public LiveData<List<Point>> getTrailPoints(int id){
        return trailDAO.getTrailPoints(id);
    }

}
