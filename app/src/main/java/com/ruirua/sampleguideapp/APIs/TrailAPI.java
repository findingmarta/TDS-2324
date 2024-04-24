package com.ruirua.sampleguideapp.APIs;

import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TrailAPI {
    @GET("trails") // Route
    Call<List<JsonElement>> getTrails() throws IOException;

}
