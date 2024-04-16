package com.ruirua.sampleguideapp.APIs;

import com.google.gson.JsonElement;
import com.ruirua.sampleguideapp.model.App;
import com.ruirua.sampleguideapp.model.AppWith;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AppAPI {
    @GET("app")
    //Call<List<AppWith>> getApps() throws IOException;
    Call<List<JsonElement>> getApps() throws IOException;
}
