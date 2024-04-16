package com.ruirua.sampleguideapp.APIs;

import com.google.gson.JsonElement;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.model.TrailWith;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TrailAPI {
    @GET("trails") // Route
    Call<List<JsonElement>> getTrails() throws IOException;

}
