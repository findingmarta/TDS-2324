package com.ruirua.sampleguideapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "social",indices = @Index(value = {"id"},unique = true))
public class Social {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "app_id")
    private int app_id;
    @ColumnInfo(name = "social_name")
    private String social_name;
    @ColumnInfo(name = "social_url")
    private String social_url;
    @ColumnInfo(name = "social_share_link")
    private String social_share_link;
    @ColumnInfo(name = "social_app")
    private String social_app;

    public Social(String social_name, String social_url, String social_share_link, String social_app) {
        this.social_name = social_name;
        this.social_url = social_url;
        this.social_share_link = social_share_link;
        this.social_app = social_app;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }
    public String getSocial_name() {
        return social_name;
    }

    public void setSocial_name(String social_name) {
        this.social_name = social_name;
    }

    public String getSocial_url() {
        return social_url;
    }

    public void setSocial_url(String social_url) {
        this.social_url = social_url;
    }

    public String getSocial_share_link() {
        return social_share_link;
    }

    public void setSocial_share_link(String social_share_link) {
        this.social_share_link = social_share_link;
    }

    public String getSocial_app() {
        return social_app;
    }

    public void setSocial_app(String social_app) {
        this.social_app = social_app;
    }
}
