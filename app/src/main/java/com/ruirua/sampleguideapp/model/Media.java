package com.ruirua.sampleguideapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "media",indices = @Index(value = {"id"},unique = true))
public class Media {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "media_file")
    private String media_file;
    @ColumnInfo(name = "media_type")
    private String media_type;
    @ColumnInfo(name = "point_id")
    private int point_id;

    public Media(int id, String media_file, String media_type, int point_id) {
        this.id = id;
        this.media_file = media_file;
        this.media_type = media_type;
        this.point_id = point_id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedia_file() {
        return media_file;
    }

    public void setMedia_file(String media_file) {
        this.media_file = media_file;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public int getPoint_id() {
        return point_id;
    }

    public void setPoint_id(int point_id) {
        this.point_id = point_id;
    }
}


