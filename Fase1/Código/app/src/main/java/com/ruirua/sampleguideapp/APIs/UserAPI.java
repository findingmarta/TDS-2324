package com.ruirua.sampleguideapp.APIs;

import com.ruirua.sampleguideapp.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserAPI {
    @GET("user")
    Call<User> getUser(@Header("Cookie") String cookiesString);  // The header's name (Cookie) and the string format can be inspected on the API's page

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> login(@Field("username") String user, @Field("email") String email, @Field("password") String password);
}
