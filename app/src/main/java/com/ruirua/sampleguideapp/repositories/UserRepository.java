package com.ruirua.sampleguideapp.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.gson.JsonElement;
import com.ruirua.sampleguideapp.BuildConfig;
import com.ruirua.sampleguideapp.DAOs.UserDAO;
import com.ruirua.sampleguideapp.database.GuideDatabase;
import com.ruirua.sampleguideapp.model.User;
import com.ruirua.sampleguideapp.APIs.UserAPI;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    public UserDAO userDAO;
    public MediatorLiveData<List<User>> allUsers;

    UserAPI api;
    String cookies;

    public UserRepository(Application application){
        GuideDatabase database = GuideDatabase.getInstance(application);
        userDAO = database.userDAO();
        allUsers = new MediatorLiveData<>();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BuildConfig.BRAGUIDE_BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(UserAPI.class);

        allUsers.addSource(
                userDAO.getUsers(), localUsers -> {
                    // TODO: ADD cache validation logic
                    if (localUsers != null && !localUsers.isEmpty()) {
                        allUsers.setValue(localUsers);
                    } else {
                        makeRequest();
                    }
                }
        );
    }

    public void insert(List<JsonElement> users){
        new InsertAsyncTask(userDAO).execute(users);
    }

    private void makeRequest() {
        Call<List<JsonElement>> call = api.getUsers();

        call.enqueue(new retrofit2.Callback<List<JsonElement>>() {
            @Override
            public void onResponse(Call<List<JsonElement>> call, Response<List<JsonElement>> response) {
                if(response.isSuccessful()) {
                    Log.e("USEEEEEEEEEEEEEEEEEEEER",response.body().toString());
                    insert(response.body());
                }
                else{
                    Log.e("ERROOOOOOOOOOOOOO",response.body().toString());
                    Log.e("main", "onFailure: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<JsonElement>> call, Throwable t) {
                Log.e("main", "onFailure: " + t.getMessage());
            }
        });
    }

    private static class InsertAsyncTask extends AsyncTask<List<JsonElement>,Void,Void> {
        private UserDAO userDAO;

        public InsertAsyncTask(UserDAO catDao) {
            this.userDAO=catDao;
        }

        @Override
        protected Void doInBackground(List<JsonElement>... lists) {
            //userDAO.insertUsers(lists[0]);
            return null;
        }
    }


    public void login(String username, String password){

        // Send a POST to the API
        Call<ResponseBody> call = api.login(username,password);

        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    Log.e("COOOOOKIEEEEESSSSS", response.toString());


                }
                else{
                    Log.e("main", "onFailure: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("main", "onFailure: " + t.getMessage());
            }
        });
    }



    public LiveData<List<User>> getAllUsers(){
        return allUsers;
    }

}

