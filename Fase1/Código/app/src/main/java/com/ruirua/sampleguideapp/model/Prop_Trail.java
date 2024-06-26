package com.ruirua.sampleguideapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(tableName = "prop_trail",indices = @Index(value = {"id"},unique = true))
public class Prop_Trail {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "value")
    private String value;
    @ColumnInfo(name = "attrib")
    private String attrib;

    @ColumnInfo(name = "trail_id")
    private int trail_id;

    public Prop_Trail(int id, String value, String attrib, int trail_id) {
        this.id = id;
        this.trail_id = trail_id;
        this.value = value;
        this.attrib = attrib;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAttrib() {
        return attrib;
    }

    public void setAttrib(String attrib) {
        this.attrib = attrib;
    }

    @Override
    public String toString() {
        return "Prop_Trail{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", attrib='" + attrib + '\'' +
                ", trail_id=" + trail_id +
                '}';
    }
}
