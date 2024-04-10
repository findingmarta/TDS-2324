package com.ruirua.sampleguideapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "point",indices = @Index(value = {"id"},unique = true))
public class Point {                                                                         // TODO Faltam alguns campos
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
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

    public Point(int id, String point_name, String point_desc, float point_lat, float point_lng, float point_alt) {
        this.id = id;
        this.point_name = point_name;
        this.point_desc = point_desc;
        this.point_lat = point_lat;
        this.point_lng = point_lng;
        this.point_alt = point_alt;
    }

    public Point(Point p) {
        this.id = p.id;
        this.point_name = p.point_name;
        this.point_desc = p.point_desc;
        this.point_lat = p.point_lat;
        this.point_lng = p.point_lng;
        this.point_alt = p.point_alt;
    }

    public Point getPoint() {
        return this;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
