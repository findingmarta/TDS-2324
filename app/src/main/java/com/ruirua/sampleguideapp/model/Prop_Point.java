package com.ruirua.sampleguideapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "real_pin",indices = @Index(value = {"id"},unique = true))
public class Prop_Point {
    @PrimaryKey//(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;
    @ColumnInfo(name = "value")
    private String value;
    @ColumnInfo(name = "attrib")
    private String attrib;
    @ColumnInfo(name = "pin")
    private Integer pin;

    public Prop_Point(Integer id, String value, String attrib, Integer pin) {
        this.id = id;
        this.value = value;
        this.attrib = attrib;
        this.pin = pin;
    }

    public Prop_Point(Prop_Point p) {
        this.id = p.id;
        this.value = p.value;
        this.attrib = p.attrib;
        this.pin = p.pin;
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

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }
}
