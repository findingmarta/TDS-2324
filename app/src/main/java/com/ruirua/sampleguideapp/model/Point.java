package com.ruirua.sampleguideapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "point",indices = @Index(value = {"pointId"},unique = true))
public class Point implements Serializable {
    @PrimaryKey
    @ColumnInfo(name = "pointId")
    private int pointId;
    @ColumnInfo(name = "pin_name")
    private String point_name;
    @ColumnInfo(name = "pin_desc")
    private String point_desc;
    @ColumnInfo(name = "pin_lat")
    private float point_lat;
    @ColumnInfo(name = "pin_lng")
    private float point_lng;
    @ColumnInfo(name = "pin_alt")
    private float point_alt;

    public Point(int pointId, String point_name, String point_desc, float point_lat, float point_lng, float point_alt) {
        this.pointId = pointId;
        this.point_name = point_name;
        this.point_desc = point_desc;
        this.point_lat = point_lat;
        this.point_lng = point_lng;
        this.point_alt = point_alt;
    }

    public Point(Point p) {
        this.pointId = p.pointId;
        this.point_name = p.point_name;
        this.point_desc = p.point_desc;
        this.point_lat = p.point_lat;
        this.point_lng = p.point_lng;
        this.point_alt = p.point_alt;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public String getPoint_name() {
        return point_name;
    }

    public void setPoint_name(String point_name) {
        this.point_name = point_name;
    }

    public String getPoint_desc() {
        return point_desc;
    }

    public void setPoint_desc(String point_desc) {
        this.point_desc = point_desc;
    }

    public float getPoint_lat() {
        return point_lat;
    }

    public void setPoint_lat(float point_lat) {
        this.point_lat = point_lat;
    }

    public float getPoint_lng() {
        return point_lng;
    }

    public void setPoint_lng(float point_lng) {
        this.point_lng = point_lng;
    }

    public float getPoint_alt() {
        return point_alt;
    }

    public void setPoint_alt(float point_alt) {
        this.point_alt = point_alt;
    }

    public Point clone(){
        return new Point(this);
    }

    @Override
    public String toString() {
        return "Point{" +
                "pointId=" + pointId +
                ", point_name='" + point_name + '\'' +
                ", point_desc='" + point_desc + '\'' +
                ", point_lat=" + point_lat +
                ", point_lng=" + point_lng +
                ", point_alt=" + point_alt +
                '}';
    }
}
