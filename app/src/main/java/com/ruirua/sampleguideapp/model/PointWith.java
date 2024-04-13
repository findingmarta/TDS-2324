package com.ruirua.sampleguideapp.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PointWith {
    @Embedded
    public Point point;
    @Relation(
            parentColumn = "id",
            entityColumn = "pointId"
    )
    public List<Media> medias;

    @Relation(
            parentColumn = "id",
            entityColumn = "pointId"
    )
    public List<Prop_Point> prop_point;

    public PointWith(Point point, List<Media> medias, List<Prop_Point> prop_point) {
        this.point = point;
        this.medias = medias;
        this.prop_point = prop_point;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    public List<Prop_Point> getProp_point() {
        return prop_point;
    }

    public void setProp_point(List<Prop_Point> prop_point) {
        this.prop_point = prop_point;
    }
}
