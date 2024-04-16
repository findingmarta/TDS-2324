package com.ruirua.sampleguideapp.APIs;

import com.google.gson.JsonElement;
import com.ruirua.sampleguideapp.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserAPI {
    @GET("user")
    Call<List<JsonElement>> getUsers();

    @FormUrlEncoded
    @POST("user")
    Call<ResponseBody> login(@Field("username") String user, @Field("password") String password);
}
