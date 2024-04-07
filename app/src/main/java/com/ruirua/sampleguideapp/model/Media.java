package com.ruirua.sampleguideapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "medias",indices = @Index(value = {"id"},unique = true))
public class Media {
    @PrimaryKey
    @NonNull
    //@SerializedName("id")
    @ColumnInfo(name = "id")
    public Integer id;
    @ColumnInfo(name = "media_file")
    public String media_file;
    @ColumnInfo(name = "media_type")
    public String media_type;
    @ColumnInfo(name = "media_type")
    public Integer media_pin;

    public Media(Integer id, String media_file, String media_type, Integer media_pin) {
        this.id = id;
        this.media_file = media_file;
        this.media_type = media_type;
        this.media_pin = media_pin;
    }

    public Media(Media m) {
        this.id = m.id;
        this.media_file = m.media_file;
        this.media_type = m.media_type;
        this.media_pin = m.media_pin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getMedia_pin() {
        return media_pin;
    }

    public void setMedia_pin(Integer media_pin) {
        this.media_pin = media_pin;
    }

    public Media clone(){
        return new Media(this);
    }
}


