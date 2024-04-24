package com.ruirua.sampleguideapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "partner",indices = @Index(value = {"id"},unique = true))
public class Partner {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "app_id")
    private int app_id;
    @ColumnInfo(name = "partner_name")
    private String partner_name;
    @ColumnInfo(name = "partner_phone")
    private String partner_phone;
    @ColumnInfo(name = "partner_url")
    private String partner_url;
    @ColumnInfo(name = "partner_mail")
    private String partner_mail;
    @ColumnInfo(name = "partner_desc")
    private String partner_desc;
    @ColumnInfo(name = "partner_app")
    private String partner_app;

    public Partner(String partner_name, String partner_phone, String partner_url, String partner_mail, String partner_desc, String partner_app) {
        this.partner_name = partner_name;
        this.partner_phone = partner_phone;
        this.partner_url = partner_url;
        this.partner_mail = partner_mail;
        this.partner_desc = partner_desc;
        this.partner_app = partner_app;
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

    public String getPartner_name() {
        return partner_name;
    }

    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }

    public String getPartner_phone() {
        return partner_phone;
    }

    public void setPartner_phone(String partner_phone) {
        this.partner_phone = partner_phone;
    }

    public String getPartner_url() {
        return partner_url;
    }

    public void setPartner_url(String partner_url) {
        this.partner_url = partner_url;
    }

    public String getPartner_mail() {
        return partner_mail;
    }

    public void setPartner_mail(String partner_mail) {
        this.partner_mail = partner_mail;
    }

    public String getPartner_desc() {
        return partner_desc;
    }

    public void setPartner_desc(String partner_desc) {
        this.partner_desc = partner_desc;
    }

    public String getPartner_app() {
        return partner_app;
    }

    public void setPartner_app(String partner_app) {
        this.partner_app = partner_app;
    }
}
