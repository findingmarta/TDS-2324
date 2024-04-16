package com.ruirua.sampleguideapp.model;

import androidx.room.Entity;

@Entity(primaryKeys = {"userId", "pointId"})
public class UserPointCrossRef {
    public int userId;
    public int pointId;

    public UserPointCrossRef(int userId, int pointId) {
        this.userId = userId;
        this.pointId = pointId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }
}
