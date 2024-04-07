package com.ruirua.sampleguideapp.database;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ruirua.sampleguideapp.DAOs.PointDAO;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.model.User;
import com.ruirua.sampleguideapp.model.Point;

import com.ruirua.sampleguideapp.DAOs.TrailDAO;

@Database(entities = {Trail.class,
                      User.class,
                      Point.class}, version = 964, exportSchema = false)

public abstract class GuideDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "BraGuide";

    public abstract TrailDAO trailDAO();
    public abstract PointDAO pointDAO();

    public static volatile GuideDatabase INSTANCE = null;

    public static GuideDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (GuideDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), GuideDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
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
        private TrailDAO trailDao;
        private PointDAO pointDAO;

        public PopulateDbAsyn(GuideDatabase catDatabase) {
            trailDao = catDatabase.trailDAO();
            pointDAO = catDatabase.pointDAO();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            trailDao.deleteAll();
            pointDAO.deleteAll();
            return null;
        }
    }
}