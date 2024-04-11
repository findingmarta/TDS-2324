package com.ruirua.sampleguideapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(tableName = "real_trail",indices = @Index(value = {"id"},unique = true))
public class Prop_Trail {

    @PrimaryKey//(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;
    @ColumnInfo(name = "value")
    private String value;
    @ColumnInfo(name = "attrib")
    private String attrib;
    @ColumnInfo(name = "trail")
    private Integer trail;

    public Prop_Trail(Integer id, String value, String attrib, Integer trail) {
        this.id = id;
        this.value = value;
        this.attrib = attrib;
        this.trail = trail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getTrail() {
        return trail;
    }

    public void setTrail(Integer trail) {
        this.trail = trail;
    }
}
