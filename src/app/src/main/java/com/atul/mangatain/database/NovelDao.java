package com.atul.mangatain.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.atul.mangatain.MTConstants;
import com.atul.mangatain.model.Novel;

import java.util.List;

@Dao
public interface NovelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Novel novel);

    @Delete
    void delete(Novel novel);

    @Query("SELECT * FROM " + MTConstants.NOVEL_TABLE)
    LiveData<List<Novel>> all();
}
