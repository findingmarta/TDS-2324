package com.ruirua.sampleguideapp.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ruirua.sampleguideapp.BuildConfig;
import com.ruirua.sampleguideapp.DAOs.AppDAO;
import com.ruirua.sampleguideapp.database.GuideDatabase;
import com.ruirua.sampleguideapp.APIs.AppAPI;
import com.ruirua.sampleguideapp.model.App;
import com.ruirua.sampleguideapp.model.AppWith;
import com.ruirua.sampleguideapp.model.Contact;
import com.ruirua.sampleguideapp.model.Partner;
import com.ruirua.sampleguideapp.model.Social;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppRepository {
    public AppDAO appDAO;
    public MediatorLiveData<AppWith> appWith;

    public AppRepository(Application application){
        GuideDatabase database = GuideDatabase.getInstance(application);
        appDAO = database.appDAO();
        appWith = new MediatorLiveData<>();
        appWith.addSource(
                appDAO.getAppWith(), localApps -> {
                    // TODO: ADD cache validation logic
                    if (localApps != null) {
                        appWith.setValue(localApps);
                    } else {
                        try {
                            makeRequest();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
    }

    public void insert(JsonElement app){
        new InsertAsyncTask(appDAO).execute(app);
    }

    private void makeRequest() throws IOException {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BuildConfig.BRAGUIDE_BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        AppAPI api = retrofit.create(AppAPI.class);
        Call<List<JsonElement>> call = api.getApps();


        call.enqueue(new retrofit2.Callback<List<JsonElement>>() {
            @Override
            public void onResponse(Call<List<JsonElement>> call, Response<List<JsonElement>> response) {
                if(response.isSuccessful()) {
                    insert(response.body().get(0));
                }
                else{
                    Log.e("main", "onFailure: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<JsonElement>> call, Throwable t) {
                Log.e("main", "onFailure: " + t.getMessage());
                Log.e("main", "message: "+ t.getCause());
            }
        });
    }

    private static class InsertAsyncTask extends AsyncTask<JsonElement,Void,Void> {
        private AppDAO appDAO;

        public InsertAsyncTask(AppDAO appDao) {
            this.appDAO=appDao;
        }

        @Override
        protected Void doInBackground(JsonElement... lists) {
            JsonObject jsonObject = lists[0].getAsJsonObject();

            App app = new App(jsonObject.get("app_name").getAsString(), jsonObject.get("app_desc").getAsString(),jsonObject.get("app_landing_page_text").getAsString());
            appDAO.insertApp(app);

           List<Social> socials = new ArrayList<>();
           JsonArray jsonArray = jsonObject.get("socials").getAsJsonArray();
           for (JsonElement element : jsonArray) {
               Social social = setSocial(element);
               socials.add(social);
           }
           appDAO.insertSocials(socials);

            List<Contact> contacts = new ArrayList<>();
            jsonArray = jsonObject.get("contacts").getAsJsonArray();
            for (JsonElement element : jsonArray) {
                Contact contact = setContact(element);
                contacts.add(contact);
            }
            appDAO.insertContacts(contacts);

            List<Partner> partners = new ArrayList<>();
            jsonArray = jsonObject.get("partners").getAsJsonArray();
            for (JsonElement element : jsonArray) {
                Partner partner = setPartner(element);
                partners.add(partner);
            }
            appDAO.insertPartners(partners);
            return null;
        }
    }

    @NonNull
    private static Social setSocial(JsonElement element) {
        JsonObject jsonObject1 = element.getAsJsonObject();

        String social_name = jsonObject1.get("social_name").getAsString();
        String social_url = jsonObject1.get("social_url").getAsString();
        String social_share_link = jsonObject1.get("social_share_link").getAsString();
        String social_app = jsonObject1.get("social_app").getAsString();
        return new Social(social_name,social_url,social_share_link,social_app);
    }

    @NonNull
    private static Contact setContact(JsonElement element) {
        JsonObject jsonObject = element.getAsJsonObject();

        String contact_name = jsonObject.get("contact_name").getAsString();
        String contact_phone = jsonObject.get("contact_phone").getAsString();
        String contact_url = jsonObject.get("contact_url").getAsString();
        String contact_mail = jsonObject.get("contact_mail").getAsString();
        String contact_desc = jsonObject.get("contact_desc").getAsString();
        String contact_app = jsonObject.get("contact_app").getAsString();
        return new Contact(contact_name,contact_phone,contact_url,contact_mail,contact_desc,contact_app);
    }

    @NonNull
    private static Partner setPartner(JsonElement element) {
        JsonObject jsonObject = element.getAsJsonObject();
        String partner_name = jsonObject.get("partner_name").getAsString();
        String partner_phone = jsonObject.get("partner_phone").getAsString();
        String partner_url = jsonObject.get("partner_url").getAsString();
        String partner_mail = jsonObject.get("partner_mail").getAsString();
        String partner_desc = jsonObject.get("partner_desc").getAsString();
        String partner_app = jsonObject.get("partner_app").getAsString();
        return new Partner(partner_name,partner_phone,partner_url,partner_mail,partner_desc,partner_app);
    }

    public LiveData<AppWith> getAppWith(){
        return appWith;
    }

    //public LiveData<List<Contact>> getContacts(){
        //return appDAO.getContacts();
    //}

}
