package com.ruirua.sampleguideapp.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "trail",indices = @Index(value = {"id"},unique = true))
public class Trail {                                                                    // TODO Faltam alguns campos
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("trail_img")
    @ColumnInfo(name = "trail_img")
    private String trail_image;

    @ColumnInfo(name = "trail_name")
    private String trail_name;

    @ColumnInfo(name = "trail_desc")
    private String trail_desc;

    @ColumnInfo(name = "trail_duration")
    private int trail_duration;

    @ColumnInfo(name = "trail_difficulty")
    private String trail_difficulty;

    public Trail(int id, String trail_image, String trail_name, String trail_desc, int trail_duration, String trail_difficulty) {
        this.id = id;
        this.trail_image = trail_image;
        this.trail_name = trail_name;
        this.trail_desc = trail_desc;
        this.trail_duration = trail_duration;
        this.trail_difficulty = trail_difficulty;
    }

    public Trail getTrail() {
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrail_image() {
        return trail_image;
    }

    public void setTrail_image(String trail_image) {
        this.trail_image = trail_image;
    }

    public String getTrail_name() {
        return trail_name;
    }

    public void setTrail_name(String trail_name) {
        this.trail_name = trail_name;
    }

    public String getTrail_desc() {
        return trail_desc;
    }

    public void setTrail_desc(String trail_desc) {
        this.trail_desc = trail_desc;
    }

    public int getTrail_duration() {
        return trail_duration;
    }

    public void setTrail_duration(int trail_duration) {
        this.trail_duration = trail_duration;
    }

    public String getTrail_difficulty() {
        return trail_difficulty;
    }

    public void setTrail_difficulty(String trail_difficulty) {
        this.trail_difficulty = trail_difficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trail trail = (Trail) o;
        return id == trail.id &&
                Objects.equals(trail_image, trail.trail_image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trail_image);
    }
}
