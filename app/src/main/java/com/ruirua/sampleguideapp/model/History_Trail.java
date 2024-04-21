package com.ruirua.sampleguideapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


import com.ruirua.sampleguideapp.model.Converters.DateTypeConverter;

import java.util.Date;

@Entity(tableName = "history_trail")
@TypeConverters({DateTypeConverter.class})
public class History_Trail {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "trail_id")
    private int trail_id;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "travelled_time")
    private int travelled_time;

    @ColumnInfo(name = "travelled_distance")
    private int travelled_distance;

    public History_Trail(int id, int trail_id, Date date, int travelled_time, int travelled_distance) {
        this.id = id;
        this.trail_id = trail_id;
        this.date = date;
        this.travelled_time = travelled_time;
        this.travelled_distance = travelled_distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrail_id() {
        return trail_id;
    }

    public void setTrail_id(int trail_id) {
        this.trail_id = trail_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTravelled_time() {
        return travelled_time;
    }

    public void setTravelled_time(int travelled_time) {
        this.travelled_time = travelled_time;
    }

    public int getTravelled_distance() {
        return travelled_distance;
    }

    public void setTravelled_distance(int travelled_distance) {
        this.travelled_distance = travelled_distance;
    }

    @Override
    public String toString() {
        return "History_Trail{" +
                "id=" + id +
                ", trail_id=" + trail_id +
                ", date=" + date +
                ", travelled_time=" + travelled_time +
                ", travelled_distance=" + travelled_distance +
                '}';
    }
}
