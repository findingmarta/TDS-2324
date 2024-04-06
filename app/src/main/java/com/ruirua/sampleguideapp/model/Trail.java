package com.ruirua.sampleguideapp.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "trails",indices = @Index(value = {"id"},unique = true))            // TODO Tirar o public das variáveis e torná-las private
public class Trail {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public String id;

    @SerializedName("trail_img")
    @ColumnInfo(name = "trail_img")
    public String trail_image;

    @ColumnInfo(name = "trail_name")
    public String trail_name;

    @ColumnInfo(name = "trail_desc")
    public String trail_desc;

    @ColumnInfo(name = "trail_duration")
    public Integer trail_duration;

    @ColumnInfo(name = "trail_difficulty")
    public String trail_difficulty;

    public Trail(@NonNull String id, String trail_image, String trail_name, String trail_desc, int trail_duration, String trail_difficulty) {
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

    public String getTrailId() {
        return id;
    }

    public void setTrailId(String id) {
        this.id = id;
    }


    public String getTrailImage() {
        return trail_image;
    }

    public void setTrailImage(String trail_img) {
        this.trail_image = trail_image;
    }

    public String getTrailName() {
        return trail_name;
    }

    public void setTrailName(String trail_name) {
        this.trail_name = trail_name;
    }

    public String getTrailDesc() {
        return trail_desc;
    }

    public void setTrailDesc(String trail_desc) {
        this.trail_desc = trail_desc;
    }

    public int getTrailDuration() {
        return trail_duration;
    }

    public void setTrailDuration(int trail_duration) {
        this.trail_duration = trail_duration;
    }

    public String getTrailDifficulty() {
        return trail_difficulty;
    }

    public void setTrailDifficulty(String trail_difficulty) {
        this.trail_difficulty = trail_difficulty;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trail trail = (Trail) o;
        return id.equals(trail.id) &&
                Objects.equals(trail_image, trail.trail_image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trail_image);
    }
}
