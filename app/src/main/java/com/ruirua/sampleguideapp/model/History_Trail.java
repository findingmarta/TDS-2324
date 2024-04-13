package com.ruirua.sampleguideapp.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.Date;
import java.util.List;

public class History_Trail {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(UserPointCrossRef.class)
    )
    public List<Trail> trails;

    public int duration;
    public Date date;

    public History_Trail(User user, List<Trail> trails, int duration, Date date) {
        this.user = user;
        this.trails = trails;
        this.duration = duration;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Trail> getTrails() {
        return trails;
    }

    public void setTrails(List<Trail> trails) {
        this.trails = trails;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
