package com.ruirua.sampleguideapp.model;

import androidx.room.Entity;
import androidx.room.Index;

@Entity(primaryKeys = {"userId", "trailId"},indices = @Index(value = {"userId", "trailId"},unique = true))
public class UserTrailCrossRef {
    public int userId;
    public int trailId;

    public UserTrailCrossRef(int userId, int trailId) {
        this.userId = userId;
        this.trailId = trailId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTrailId() {
        return trailId;
    }

    public void setTrailId(int trailId) {
        this.trailId = trailId;
    }
}
