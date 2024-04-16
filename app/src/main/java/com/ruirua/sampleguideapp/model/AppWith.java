package com.ruirua.sampleguideapp.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class AppWith {
    @Embedded
    public App app;

    @Relation(
            parentColumn = "id",
            entityColumn = "app_id"
    )
    public List<Partner> partners;

    @Relation(
            parentColumn = "id",
            entityColumn = "app_id"
    )
    public List<Contact> contacts;

    @Relation(
            parentColumn = "id",
            entityColumn = "app_id"
    )
    public List<Social> socials;

    public AppWith(App app, List<Partner> partners, List<Contact> contacts, List<Social> socials) {
        this.app = app;
        this.partners = partners;
        this.contacts = contacts;
        this.socials = socials;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public List<Partner> getPartners() {
        return partners;
    }

    public void setPartners(List<Partner> partners) {
        this.partners = partners;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Social> getSocials() {
        return socials;
    }

    public void setSocials(List<Social> socials) {
        this.socials = socials;
    }

    @Override
    public String toString() {
        return "AppWith{" +
                "app=" + app +
                ", partners=" + partners +
                ", contacts=" + contacts +
                ", socials=" + socials +
                '}';
    }
}
