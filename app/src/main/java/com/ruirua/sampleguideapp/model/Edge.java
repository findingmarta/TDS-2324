package com.ruirua.sampleguideapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "edge",indices = @Index(value = {"id"},unique = true))
public class Edge {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "edge_transport")
    private String edge_transport;
    @ColumnInfo(name = "edge_duration")
    private int edge_duration;
    @ColumnInfo(name = "edge_desc")
    private String edge_desc;

    @ColumnInfo(name = "trail_id") // edge_trail
    private int trail_id;

    //@SerializedName("edge_start")
    @ColumnInfo(name = "edge_start")
    public int edge_start;

    //@SerializedName("edge_end")
    @ColumnInfo(name = "edge_end")
    public int edge_end;


    public Edge(int id, String edge_transport, int edge_duration, String edge_desc, int trail_id, int edge_start, int edge_end) {
        this.id = id;
        this.edge_transport = edge_transport;
        this.edge_duration = edge_duration;
        this.edge_desc = edge_desc;
        this.trail_id = trail_id;
        this.edge_start = edge_start;
        this.edge_end = edge_end;
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

    public String getEdge_transport() {
        return edge_transport;
    }

    public void setEdge_transport(String edge_transport) {
        this.edge_transport = edge_transport;
    }

    public int getEdge_duration() {
        return edge_duration;
    }

    public void setEdge_duration(int edge_duration) {
        this.edge_duration = edge_duration;
    }

    public String getEdge_desc() {
        return edge_desc;
    }

    public void setEdge_desc(String edge_desc) {
        this.edge_desc = edge_desc;
    }

    public int getEdge_start() {
        return edge_start;
    }

    public void setEdge_start(int edge_start) {
        this.edge_start = edge_start;
    }

    public int getEdge_end() {
        return edge_end;
    }

    public void setEdge_end(int edge_end) {
        this.edge_end = edge_end;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "id=" + id +
                ", edge_transport='" + edge_transport + '\'' +
                ", edge_duration=" + edge_duration +
                ", edge_desc='" + edge_desc + '\'' +
                ", edge_trail='" + trail_id + '\'' +
                '}';
    }
}
