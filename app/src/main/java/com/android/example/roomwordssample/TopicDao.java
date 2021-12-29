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
public interface TopicDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Topic topic);

    @Query("DELETE FROM topic_table")
    void deleteAll();

    @Delete
    void deleteTopic(Topic topic);

    @Query("SELECT * from topic_table LIMIT 1")
    Topic[] getAnyTopic();

    @Query("SELECT * from topic_table ORDER BY topic ASC")
    LiveData<List<Topic>> getAllTopics();

    @Update
    void update(Topic... topic);
}
