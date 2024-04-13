package com.ruirua.sampleguideapp.APIs;

import com.ruirua.sampleguideapp.model.App;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AppAPI {
    @GET("app")
    Call<List<App>> getApps();
}
