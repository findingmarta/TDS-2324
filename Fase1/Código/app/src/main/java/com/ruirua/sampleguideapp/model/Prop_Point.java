package com.ruirua.sampleguideapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "prop_point",indices = @Index(value = {"id"},unique = true))
public class Prop_Point {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "value")
    private String value;
    @ColumnInfo(name = "attrib")
    private String attrib;
    @ColumnInfo(name = "point_id")
    private int point_id;

    public Prop_Point(int id, String value, String attrib, int point_id) {
        this.id = id;
        this.value = value;
        this.attrib = attrib;
        this.point_id = point_id;
    }

    public Prop_Point(Prop_Point p) {
        this.id = p.id;
        this.value = p.value;
        this.attrib = p.attrib;
        this.point_id = p.point_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getPoint_id() {
        return point_id;
    }

    public void setPoint_id(int point_id) {
        this.point_id = point_id;
    }
}
