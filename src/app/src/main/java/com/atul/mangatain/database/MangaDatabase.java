package com.atul.mangatain.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.atul.mangatain.MTConstants;
import com.atul.mangatain.model.Manga;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Manga.class}, version = 1, exportSchema = false)
public abstract class MangaDatabase extends RoomDatabase {

    public static final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
    private static volatile MangaDatabase INSTANCE;

    public static MangaDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MangaDatabase.class) {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MangaDatabase.class, MTConstants.MANGA_DB)
                            .build();
            }
        }
        return INSTANCE;
    }

    public abstract MangaDao dao();
}
