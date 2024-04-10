package com.ruirua.sampleguideapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "app",indices = @Index(value = {"id"},unique = true))   // TODO Faltam alguns campos
public class App {
    @PrimaryKey                 //(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "app_name")
    private String app_name;
    @ColumnInfo(name = "app_desc")
    private String app_desc;
    @ColumnInfo(name = "app_landing_page_text")
    private String app_landing_page_text;

    public App(int id, String app_name, String app_desc, String app_landing_page_text) {
        this.id = id;
        this.app_name = app_name;
        this.app_desc = app_desc;
        this.app_landing_page_text = app_landing_page_text;
    }

    public App(App a) {
        this.id = a.id;
        this.app_name = a.app_name;
        this.app_desc = a.app_desc;
        this.app_landing_page_text = a.app_landing_page_text;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_desc() {
        return app_desc;
    }

    public void setApp_desc(String app_desc) {
        this.app_desc = app_desc;
    }

    public String getApp_landing_page_text() {
        return app_landing_page_text;
    }

    public void setApp_landing_page_text(String app_landing_page_text) {
        this.app_landing_page_text = app_landing_page_text;
    }

    public App clone(){
        return new App(this);
    }
}