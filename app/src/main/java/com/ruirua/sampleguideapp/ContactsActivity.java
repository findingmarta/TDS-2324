package com.ruirua.sampleguideapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ruirua.sampleguideapp.adapters.ContactsRecyclerViewAdapter;
import com.ruirua.sampleguideapp.adapters.TrailsRecyclerViewAdapter;
import com.ruirua.sampleguideapp.model.App;
import com.ruirua.sampleguideapp.model.AppWith;
import com.ruirua.sampleguideapp.model.Contact;
import com.ruirua.sampleguideapp.viewModel.AppViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends GeneralActivity{

    private ContactsRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_contacts;
    }
    @Override
    protected int getNavBarItemSelected () {
        return R.id.SOS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = findViewById(R.id.rv_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));



        setInfo();
    }

    public void setInfo(){

        AppViewModel avm = new ViewModelProvider(this).get(AppViewModel.class);
        LiveData<AppWith> appsData = avm.getApp();
        appsData.observe(this, appWith -> {
            if (appWith != null){
                List<Contact> contacts = appWith.getContacts();
                adapter = new ContactsRecyclerViewAdapter(contacts);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}