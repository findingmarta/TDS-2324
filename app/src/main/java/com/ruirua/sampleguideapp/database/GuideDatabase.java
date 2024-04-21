package com.ruirua.sampleguideapp.database;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ruirua.sampleguideapp.DAOs.AppDAO;
import com.ruirua.sampleguideapp.DAOs.HistoryPointDAO;
import com.ruirua.sampleguideapp.DAOs.HistoryTrailDAO;
import com.ruirua.sampleguideapp.DAOs.UserDAO;
import com.ruirua.sampleguideapp.DAOs.PointDAO;
import com.ruirua.sampleguideapp.DAOs.TrailDAO;
import com.ruirua.sampleguideapp.DAOs.MediaDAO;

import com.ruirua.sampleguideapp.model.App;
import com.ruirua.sampleguideapp.model.Contact;
import com.ruirua.sampleguideapp.model.Edge;
import com.ruirua.sampleguideapp.model.History_Point;
import com.ruirua.sampleguideapp.model.History_Trail;
import com.ruirua.sampleguideapp.model.Partner;
import com.ruirua.sampleguideapp.model.Prop_Point;
import com.ruirua.sampleguideapp.model.Prop_Trail;
import com.ruirua.sampleguideapp.model.Social;
import com.ruirua.sampleguideapp.model.User;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.Media;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {
        App.class,
        User.class,
        Trail.class,
        Point.class,
        Edge.class,
        Prop_Trail.class,
        Prop_Point.class,
        History_Point.class,
        History_Trail.class,
        Media.class,
        Partner.class,
        Social.class,
        Contact.class}, version = 972, exportSchema = false)

public abstract class GuideDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "BraGuide";

    public abstract AppDAO appDAO();
    public abstract UserDAO userDAO();
    public abstract TrailDAO trailDAO();
    public abstract PointDAO pointDAO();
    public abstract MediaDAO mediaDAO();
    public abstract HistoryPointDAO historyPointDAO();
    public abstract HistoryTrailDAO historyTrailDAO();


    public static volatile GuideDatabase INSTANCE = null;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static GuideDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (GuideDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), GuideDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static Callback callback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsyn(INSTANCE);
        }
    };

    static class  PopulateDbAsyn extends AsyncTask<Void,Void,Void>{
        private AppDAO appDAO;
        private UserDAO userDAO;
        private TrailDAO trailDAO;
        private PointDAO pointDAO;
        private MediaDAO mediaDAO;
        private HistoryPointDAO historyPointDAO;
        private HistoryTrailDAO historyTrailDAO;

        public PopulateDbAsyn(GuideDatabase database) {
            appDAO = database.appDAO();
            userDAO = database.userDAO();
            trailDAO = database.trailDAO();
            pointDAO = database.pointDAO();
            mediaDAO = database.mediaDAO();
            historyPointDAO = database.historyPointDAO();
            historyTrailDAO = database.historyTrailDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            appDAO.deleteAll();
            userDAO.deleteAll();
            trailDAO.deleteAll();
            pointDAO.deleteAll();
            mediaDAO.deleteAll();
            historyPointDAO.deleteAll();
            historyTrailDAO.deleteAll();
            return null;
        }
    }
}