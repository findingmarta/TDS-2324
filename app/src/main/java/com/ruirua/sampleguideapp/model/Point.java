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


    public Point getPoint() {
        return this;
    }

    public String getPointId() {
        return id;
    }

    public void setPointId(String id) {
        this.id = id;
    }
}
