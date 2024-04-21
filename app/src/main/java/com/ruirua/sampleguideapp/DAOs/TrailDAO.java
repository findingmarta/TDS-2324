package com.ruirua.sampleguideapp.DAOs;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ruirua.sampleguideapp.model.Edge;
import com.ruirua.sampleguideapp.model.History_Point;
import com.ruirua.sampleguideapp.model.History_Trail;
import com.ruirua.sampleguideapp.model.Media;
import com.ruirua.sampleguideapp.model.Partner;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.PointWith;
import com.ruirua.sampleguideapp.model.Prop_Point;
import com.ruirua.sampleguideapp.model.Prop_Trail;
import com.ruirua.sampleguideapp.model.Social;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.model.TrailWith;

import java.util.List;

@Dao
public interface TrailDAO {
    // INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrails(List<Trail> trails);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEdges(List<Edge> edges);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPropsTrail(List<Prop_Trail> proprieties);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPoint(Point point);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPropsPoint(List<Prop_Point> proprieties);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMedia(List<Media> medias);


    // GET
    @Query("SELECT DISTINCT * FROM trail")
    LiveData<List<Trail>> getTrails();

    @Query("SELECT * FROM trail WHERE trail.trailId = :id")
    LiveData<Trail> getTrailById(int id);

    @Transaction
    @Query("SELECT * FROM trail")
    LiveData<List<TrailWith>> getTrailWith();

    @Transaction
    @Query("SELECT * FROM trail WHERE trail.trailId = :id")
    LiveData<TrailWith> getTrailWithById(int id);



    //@Transaction
    @Query("SELECT DISTINCT point.* FROM point JOIN edge ON (point.pointId = edge.edge_start OR point.pointId = edge.edge_end) WHERE edge.trail_id = :id")
    LiveData<List<Point>> getTrailPoints(int id);

    // DELETE
    @Query("DELETE FROM trail")
    void deleteAll();

}