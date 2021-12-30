package com.android.example.roomwordssample;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Delete
    void deleteTask(Task task);

    @Query("SELECT * from task_table LIMIT 1")
    Task[] getAnyTask();

    @Query("SELECT * from task_table WHERE topicId = :topicId ORDER BY task ASC")
    LiveData<List<Task>> getAllTasks(int topicId);

    @Update
    void update(Task... task);

    @Query("SELECT * FROM task_table WHERE task = :taskId")
    LiveData<List<Task>> getTask(int taskId);

    @Transaction
    @Query("SELECT * FROM task_table")
    public List<Task> getTopicsWithTask();

}
