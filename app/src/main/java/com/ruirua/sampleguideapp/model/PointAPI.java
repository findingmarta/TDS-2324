package com.ruirua.sampleguideapp.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PointAPI {

    @GET("points")
    Call<List<Point>> getPoints();

}