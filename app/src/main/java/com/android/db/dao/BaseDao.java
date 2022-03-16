package com.android.db.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

public interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<T> item);

    @Update
    void update(T item);

    @Update
    void update(List<T> item);

    @Delete
    void delete(T item);

    @Delete
    void delete(List<T> item);

}
