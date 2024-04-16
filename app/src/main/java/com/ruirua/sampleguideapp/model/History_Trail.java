package com.ruirua.sampleguideapp.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import com.ruirua.sampleguideapp.model.Converters.DateTypeConverter;
import com.ruirua.sampleguideapp.model.Converters.TrailsTypeConverter;

import java.util.Date;
import java.util.List;

@TypeConverters({DateTypeConverter.class, TrailsTypeConverter.class})
public class History_Trail {
    @Embedded
    public User user;
    //public int duration;
    //public Date date;

    @Relation(
            parentColumn = "userId",
            entityColumn = "trailId",
            associateBy = @Junction(UserTrailCrossRef.class)
    )
    public List<Trail> trails;

    public History_Trail(User user, List<Trail> trails) {
        this.user = user;
        this.trails = trails;
        //this.duration = duration;
        //this.date = date;
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
}
