package com.ruirua.sampleguideapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "point",indices = @Index(value = {"pointId"},unique = true))
public class Point implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "pointId")
    private int pointId;
    @ColumnInfo(name = "pin_name")
    private String point_name;
    @ColumnInfo(name = "pin_desc")
    private String point_desc;
    @ColumnInfo(name = "pin_lat")
    private float point_lat;
    @ColumnInfo(name = "pin_lng")
    private float point_lng;
    @ColumnInfo(name = "pin_alt")
    private float point_alt;
    @ColumnInfo(name = "visited")
    private boolean visited;

    public Point(int pointId, String point_name, String point_desc, float point_lat, float point_lng, float point_alt) {
        this.pointId = pointId;
        this.point_name = point_name;
        this.point_desc = point_desc;
        this.point_lat = point_lat;
        this.point_lng = point_lng;
        this.point_alt = point_alt;
        this.visited = false;
    }

    public Point(Point p) {
        this.pointId = p.pointId;
        this.point_name = p.point_name;
        this.point_desc = p.point_desc;
        this.point_lat = p.point_lat;
        this.point_lng = p.point_lng;
        this.point_alt = p.point_alt;
        this.visited = p.visited;
    }

    protected Point(Parcel in) {
        pointId = in.readInt();
        point_name = in.readString();
        point_desc = in.readString();
        point_lat = in.readFloat();
        point_lng = in.readFloat();
        point_alt = in.readFloat();
        visited = in.readByte() != 0;
    }

    public static final Creator<Point> CREATOR = new Creator<Point>() {
        @Override
        public Point createFromParcel(Parcel in) {
            return new Point(in);
        }

        @Override
        public Point[] newArray(int size) {
            return new Point[size];
        }
    };

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public String getPoint_name() {
        return point_name;
    }

    public void setPoint_name(String point_name) {
        this.point_name = point_name;
    }

    public String getPoint_desc() {
        return point_desc;
    }

    public void setPoint_desc(String point_desc) {
        this.point_desc = point_desc;
    }

    public float getPoint_lat() {
        return point_lat;
    }

    public void setPoint_lat(float point_lat) {
        this.point_lat = point_lat;
    }

    public float getPoint_lng() {
        return point_lng;
    }

    public void setPoint_lng(float point_lng) {
        this.point_lng = point_lng;
    }

    public float getPoint_alt() {
        return point_alt;
    }

    public void setPoint_alt(float point_alt) {
        this.point_alt = point_alt;
    }

    public boolean getVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }


    @NonNull
    @Override
    public String toString() {
        return "Point{" +
                "pointId=" + pointId +
                ", point_name='" + point_name + '\'' +
                ", point_desc='" + point_desc + '\'' +
                ", point_lat=" + point_lat +
                ", point_lng=" + point_lng +
                ", point_alt=" + point_alt +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(pointId);
        dest.writeString(point_name);
        dest.writeString(point_desc);
        dest.writeFloat(point_lat);
        dest.writeFloat(point_lng);
        dest.writeFloat(point_alt);
        dest.writeByte((byte) (visited ? 1 : 0));
    }
}
