package com.atul.mangatain.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.atul.mangatain.MTConstants;
import com.atul.mangatain.model.Manga;

import java.util.List;

@Dao
public interface MangaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Manga manga);

    @Delete
    void delete(Manga manga);

    @Query("SELECT * FROM " + MTConstants.MANGA_TABLE)
    LiveData<List<Manga>> all();
}
