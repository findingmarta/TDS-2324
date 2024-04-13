package com.ruirua.sampleguideapp.APIs;

import com.ruirua.sampleguideapp.model.Trail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TrailAPI {

    @GET("trails") // Route
    Call<List<Trail>> getTrails();

}
