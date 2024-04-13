package com.ruirua.sampleguideapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "edge",indices = @Index(value = {"id"},unique = true))   // TODO Faltam alguns campos
public class Edge {
    @PrimaryKey                 //(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "edge_transport")
    private String edge_transport;
    @ColumnInfo(name = "edge_duration")
    private Integer edge_duration;
    @ColumnInfo(name = "edge_desc")
    private String edge_desc;
    @ColumnInfo(name = "edge_trail")
    private String edge_trail;

    @Embedded
    public Point edge_start;

    @Embedded
    public Point edge_end;


    public Edge(Integer id, String edge_transport, Integer edge_duration, String edge_desc, String edge_trail, Point edge_start, Point edge_end) {
        this.id = id;
        this.edge_transport = edge_transport;
        this.edge_duration = edge_duration;
        this.edge_desc = edge_desc;
        this.edge_trail = edge_trail;
        this.edge_start = edge_start;
        this.edge_end = edge_end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEdge_transport() {
        return edge_transport;
    }

    public void setEdge_transport(String edge_transport) {
        this.edge_transport = edge_transport;
    }

    public Integer getEdge_duration() {
        return edge_duration;
    }

    public void setEdge_duration(Integer edge_duration) {
        this.edge_duration = edge_duration;
    }

    public String getEdge_desc() {
        return edge_desc;
    }

    public void setEdge_desc(String edge_desc) {
        this.edge_desc = edge_desc;
    }

    public String getEdge_trail() {
        return edge_trail;
    }

    public void setEdge_trail(String edge_trail) {
        this.edge_trail = edge_trail;
    }

    public Point getEdge_start() {
        return edge_start;
    }

    public void setEdge_start(Point edge_start) {
        this.edge_start = edge_start;
    }

    public Point getEdge_end() {
        return edge_end;
    }

    public void setEdge_end(Point edge_end) {
        this.edge_end = edge_end;
    }
}
