package com.ruirua.sampleguideapp.repositories;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.ruirua.sampleguideapp.BuildConfig;
import com.ruirua.sampleguideapp.DAOs.UserDAO;
import com.ruirua.sampleguideapp.database.GuideDatabase;
import com.ruirua.sampleguideapp.model.User;
import com.ruirua.sampleguideapp.APIs.UserAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    private final UserDAO userDAO;
    public MediatorLiveData<List<User>> allUsers;

    MutableLiveData<Boolean> isLoggedIn;

    UserAPI api;
    Application app;

    public UserRepository(Application application){
        GuideDatabase database = GuideDatabase.getInstance(application);
        userDAO = database.userDAO();
        allUsers = new MediatorLiveData<>();
        isLoggedIn = new MutableLiveData<>();
        app = application;

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BuildConfig.BRAGUIDE_BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(UserAPI.class);

        allUsers.addSource(
                userDAO.getUsers(), localUsers -> {
                    if (localUsers != null && !localUsers.isEmpty()) {
                        allUsers.setValue(localUsers);
                    } else {
                        makeRequest();
                    }
                }
        );
    }

    public void insert(User users){
        new InsertAsyncTask(userDAO).execute(users);
    }

    private void makeRequest() {
        SharedPreferences sp = app.getSharedPreferences("BraGuia Shared Preferences",MODE_PRIVATE);
        String cookies = sp.getString("cookies",null);

        if (cookies != null){
            String cookies_formatted = formatCookies(cookies);
            Call<User> call = api.getUser(cookies_formatted);

            call.enqueue(new retrofit2.Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                    if(response.isSuccessful()) {
                        insert(response.body());
                    }
                    else{
                        try {
                            assert response.errorBody() != null;
                            Log.e("main", "onFailure: "+response.errorBody().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                    Log.e("main", "onFailure: " + t.getMessage());
                    Log.e("main", "message login: "+ t.getCause());
                }
            });
        }
    }

    private static class InsertAsyncTask extends AsyncTask<User,Void,Void> {
        private final UserDAO userDAO;

        public InsertAsyncTask(UserDAO userDao) {
            this.userDAO = userDao;
        }

        @Override
        protected Void doInBackground(User... lists) {
            userDAO.insertUser(lists[0]);
            return null;
        }
    }


    public void login(String username, String password){
        // Send a POST to the API
        Call<ResponseBody> call = api.login(username,"",password);

        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    List<String> raw_cookies = parseResponse(response);

                    if (!raw_cookies.isEmpty()){
                        parseCookies(raw_cookies);
                        isLoggedIn.postValue(true);
                    } else {
                        isLoggedIn.postValue(false);
                        Log.e("main", "onFailure: Couldn't parse the response! Cookies list empty...");
                    }
                }
                else{
                    isLoggedIn.postValue(false);
                    try {
                        assert response.errorBody() != null;
                        Log.e("main", "onFailure: "+response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                isLoggedIn.postValue(false);
                Log.e("main", "onFailure: " + t.getMessage());
                Log.e("main", "message login: "+ t.getCause());
            }
        });
    }


    public List<String> parseResponse(Response<ResponseBody> response){
        List<String> raw_cookies = new ArrayList<>();
        for (String header_name : response.headers().names()){
            if (header_name.equalsIgnoreCase("set-cookie")){
                raw_cookies = response.headers().values(header_name);
            }
        }
        return raw_cookies;
    }

    public void parseCookies(List<String> raw_cookies){
        StringBuilder cookies = new StringBuilder();

        for (String raw_cookie : raw_cookies){
            String field = raw_cookie.trim().split(";")[0];
            String cookie_value = field.trim().split("=")[1];
            cookies.append(cookie_value).append(";");
        }
        saveCookies(cookies.toString());
    }

    public void saveCookies(String cookiesString){
        SharedPreferences sp = app.getSharedPreferences("BraGuia Shared Preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("cookies",cookiesString);
        editor.apply();
    }

    public static String formatCookies(String cookies) {
        StringBuilder cookies_formated = new StringBuilder();

        String csrf_value = cookies.trim().split(";")[0];
        String session_value = cookies.trim().split(";")[1];

        cookies_formated.append("csrftoken=").append(csrf_value)
                .append(";").append("sessionid=").append(session_value);

        return cookies_formated.toString();
    }


    public LiveData<Boolean> getIsLoggedIn() {
        return isLoggedIn;
    }

    public LiveData<List<User>> getAllUsers(){
        return allUsers;
    }

    public void delete(){
        GuideDatabase.databaseWriteExecutor.execute(userDAO::deleteAll);
    }

}

