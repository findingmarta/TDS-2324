package com.ruirua.sampleguideapp.APIs;

import com.ruirua.sampleguideapp.model.Point;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PointAPI {

    @GET("pins")
    Call<List<Point>> getPoints();

}
