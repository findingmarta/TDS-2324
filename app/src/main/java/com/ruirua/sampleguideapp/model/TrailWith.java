package com.ruirua.sampleguideapp.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TrailWith {
    @Embedded
    public Trail trail;
    @Relation(
            parentColumn = "id",
            entityColumn = "trailId"
    )
    public List<Edge> edges;

    @Relation(
            parentColumn = "id",
            entityColumn = "trailId"
    )
    public List<Trail> prop_trails;

    public TrailWith(Trail trail, List<Edge> edges, List<Trail> prop_trails) {
        this.trail = trail;
        this.edges = edges;
        this.prop_trails = prop_trails;
    }

    public Trail getTrail() {
        return trail;
    }

    public void setTrail(Trail trail) {
        this.trail = trail;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public List<Trail> getProp_trails() {
        return prop_trails;
    }

    public void setProp_trails(List<Trail> prop_trails) {
        this.prop_trails = prop_trails;
    }
}
