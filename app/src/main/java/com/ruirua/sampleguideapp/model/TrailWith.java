package com.ruirua.sampleguideapp.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TrailWith {
    @Embedded
    public Trail trail;
    @Relation(
            parentColumn = "trailId",
            entityColumn = "trail_id"
    )
    public List<Edge> edges;

    @Relation(
            parentColumn = "trailId",
            entityColumn = "trail_id"
    )
    public List<Prop_Trail> prop_trails;

    public TrailWith(Trail trail, List<Edge> edges, List<Prop_Trail> prop_trails) {
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

    public List<Prop_Trail> getProp_trails() {
        return prop_trails;
    }

    public void setProp_trails(List<Prop_Trail> prop_trails) {
        this.prop_trails = prop_trails;
    }

    @Override
    public String toString() {
        return "TrailWith{" +
                "trail=" + trail +
                ", edges=" + edges +
                ", prop_trails=" + prop_trails +
                '}';
    }
}
