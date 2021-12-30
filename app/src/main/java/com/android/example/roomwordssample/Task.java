package com.android.example.roomwordssample;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "task_table")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "task")
    private String mTask;

    @ColumnInfo(name = "desc")
    private String description;

    @ColumnInfo(name = "topicId")
    private int topicId;


    public Task(@NonNull String task) {
        this.mTask = task;
    }

    /*
     * This constructor is annotated using @Ignore, because Room expects only
     * one constructor by default in an entity class.
     */

    @Ignore
    public Task(int id, @NonNull String task) {
        this.id = id;
        this.mTask = task;
    }
    @Ignore
    public Task(@NonNull String task, String desc, int topicId) {
        this.description = desc;
        this.mTask = task;
        this.topicId = topicId;
    }

    public String getTask() {
        return this.mTask;
    }

    public void setTask(String task) {
        this.mTask = task;
    }

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription () {
        return this.description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }


}
