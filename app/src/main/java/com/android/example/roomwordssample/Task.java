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

    public Task(@NonNull String task, String desc) {

        this.mTask = task;
        this.description = desc;
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

    public String getTask() {
        return this.mTask;
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


}
