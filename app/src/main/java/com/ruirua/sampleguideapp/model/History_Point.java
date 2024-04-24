package com.ruirua.sampleguideapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.ruirua.sampleguideapp.model.Converters.DateTypeConverter;

import java.util.Date;

@Entity(tableName = "history_point")
@TypeConverters({DateTypeConverter.class})
public class History_Point {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "point_id")
    private int point_id;

    @ColumnInfo(name = "date")
    private Date date;

    public History_Point(int point_id, Date date) {
        this.point_id = point_id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoint_id() {
        return point_id;
    }

    public void setPoint_id(int point_id) {
        this.point_id = point_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @NonNull
    @Override
    public String toString() {
        return "History_Point{" +
                "id=" + id +
                ", point_id=" + point_id +
                ", date=" + date +
                '}';
    }
}


