package com.ruirua.sampleguideapp.database;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ruirua.sampleguideapp.DAOs.AppDAO;
import com.ruirua.sampleguideapp.DAOs.UserDAO;
import com.ruirua.sampleguideapp.DAOs.PointDAO;
import com.ruirua.sampleguideapp.DAOs.TrailDAO;
import com.ruirua.sampleguideapp.DAOs.MediaDAO;

import com.ruirua.sampleguideapp.model.App;
import com.ruirua.sampleguideapp.model.User;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.Media;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {App.class,
                      User.class,
                      Trail.class,
                      Point.class,
                      Media.class}, version = 969, exportSchema = false)

public abstract class GuideDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "BraGuide";

    public abstract AppDAO appDAO();
    public abstract UserDAO userDAO();
    public abstract TrailDAO trailDAO();
    public abstract PointDAO pointDAO();
    public abstract MediaDAO mediaDAO();


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
        private AppDAO appDao;
        private UserDAO userDao;
        private TrailDAO trailDao;
        private PointDAO pointDAO;
        private MediaDAO mediaDao;
        public PopulateDbAsyn(GuideDatabase catDatabase) {
            appDao = catDatabase.appDAO();
            userDao = catDatabase.userDAO();
            trailDao = catDatabase.trailDAO();
            pointDAO = catDatabase.pointDAO();
            mediaDao = catDatabase.mediaDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            appDao.deleteAll();
            userDao.deleteAll();
            trailDao.deleteAll();
            pointDAO.deleteAll();
            mediaDao.deleteAll();
            return null;
        }
    }
}