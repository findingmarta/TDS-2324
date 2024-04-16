package com.ruirua.sampleguideapp.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "trail",indices = @Index(value = {"trailId"},unique = true))
public class Trail {
    @PrimaryKey
    @ColumnInfo(name = "trailId")
    private int trailId;

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

    public Trail(int trailId, String trail_image, String trail_name, String trail_desc, int trail_duration, String trail_difficulty) {
        this.trailId = trailId;
        this.trail_image = trail_image;
        this.trail_name = trail_name;
        this.trail_desc = trail_desc;
        this.trail_duration = trail_duration;
        this.trail_difficulty = trail_difficulty;
    }

    public int getTrailId() {
        return trailId;
    }

    public void setTrailId(int trailId) {
        this.trailId = trailId;
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
        return trailId == trail.trailId &&
                Objects.equals(trail_image, trail.trail_image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trailId, trail_image);
    }

    @Override
    public String toString() {
        return "Trail{" +
                "trailId=" + trailId +
                ", trail_image='" + trail_image + '\'' +
                ", trail_name='" + trail_name + '\'' +
                ", trail_desc='" + trail_desc + '\'' +
                ", trail_duration=" + trail_duration +
                ", trail_difficulty='" + trail_difficulty + '\'' +
                '}';
    }
}
