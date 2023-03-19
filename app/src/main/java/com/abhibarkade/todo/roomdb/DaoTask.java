package com.abhibarkade.todo.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoTask {
    @Query("select * from EntityTask")
    LiveData<List<EntityTask>> getTasks();

    @Insert
    void insertTask(EntityTask todo);

    @Update
    void updateTask(EntityTask todo);

    @Delete
    void deleteTask(EntityTask todo);

    @Query("DELETE FROM EntityTask")
    void truncateTable();
}
