package com.ruirua.sampleguideapp.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import com.ruirua.sampleguideapp.model.Converters.PointsTypeConverter;

import java.util.List;

@TypeConverters({PointsTypeConverter.class})
public class History_Point {
    @Embedded
    public User user;

    @Relation(
            parentColumn = "userId",
            entityColumn = "pointId",
            associateBy = @Junction(UserPointCrossRef.class)
    )
    public List<Point> points;

    public History_Point(User user, List<Point> points) {
        this.user = user;
        this.points = points;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
}
