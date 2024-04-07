package com.ruirua.sampleguideapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "points",indices = @Index(value = {"id"},unique = true))            // TODO Tirar o public das variáveis e torná-las private
public class Point {                                                                    // TODO Acabar esta classe
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public String id;
    @ColumnInfo(name = "pin_name")
    public String pin_name;
    @ColumnInfo(name = "pin_desc")
    public String pin_desc;
    @ColumnInfo(name = "pin_lat")
    public float pin_lat;
    @ColumnInfo(name = "pin_lng")
    public float pin_lng;
    @ColumnInfo(name = "pin_alt")
    public float pin_alt;

    public Point(String id, String pin_name, String pin_desc, float pin_lat, float pin_lng, float pin_alt) {
        this.id = id;
        this.pin_name = pin_name;
        this.pin_desc = pin_desc;
        this.pin_lat = pin_lat;
        this.pin_lng = pin_lng;
        this.pin_alt = pin_alt;
    }

    public Point(Point p) {
        this.id = p.id;
        this.pin_name = p.pin_name;
        this.pin_desc = p.pin_desc;
        this.pin_lat = p.pin_lat;
        this.pin_lng = p.pin_lng;
        this.pin_alt = p.pin_alt;
    }

    public Point getPoint() {
        return this;
    }

    public String getPointId() {
        return id;
    }

    public String getPin_name() {
        return pin_name;
    }

    public String getPin_desc() {
        return pin_desc;
    }

    public float getPin_lat() {
        return pin_lat;
    }

    public float getPin_lng() {
        return pin_lng;
    }

    public float getPin_alt() {
        return pin_alt;
    }

    public void setPointId(String id) {
        this.id = id;
    }

    public void setPin_name(String pin_name) {
        this.pin_name = pin_name;
    }

    public void setPin_desc(String pin_desc) {
        this.pin_desc = pin_desc;
    }

    public void setPin_lat(Boolean pin_lat) {
        this.pin_lat = pin_lat;
    }

    public void setPin_lng(Boolean pin_lng) {
        this.pin_lng = pin_lng;
    }

    public void setPin_alt(Boolean pin_alt) {
        this.pin_alt = pin_alt;
    }

    public Point clone(){
        return Point(this);
    }
}
