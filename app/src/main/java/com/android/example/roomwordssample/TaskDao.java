package com.android.example.roomwordssample;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Delete
    void deleteTopic(Task task);

    @Query("SELECT * from task_table LIMIT 1")
    Topic[] getAnyTask();

    @Query("SELECT * from task_table ORDER BY task ASC")
    LiveData<List<Topic>> getAllTasks();

    @Update
    void update(Task... task);

    @Query("SELECT * FROM task_table WHERE task = :taskId")
    LiveData<List<Topic>> getTask(int taskId);

}
