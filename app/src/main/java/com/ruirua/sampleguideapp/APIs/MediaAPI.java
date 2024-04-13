package com.ruirua.sampleguideapp.APIs;

import com.ruirua.sampleguideapp.model.Media;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MediaAPI {
    @GET("content")
    Call<List<Media>> getMedias();
}
