package com.android.example.roomwordssample;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "topic_table")
public class Topic {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "topic")
    private String mTopic;

    @ColumnInfo(name = "desc")
    private String description;

    public Topic(@NonNull String topic) {

        this.mTopic = topic;
        this.description = "DescPrueba";
    }

    /*
     * This constructor is annotated using @Ignore, because Room expects only
     * one constructor by default in an entity class.
     */

    @Ignore
    public Topic(int id, @NonNull String topic) {
        this.id = id;
        this.mTopic = topic;
    }

    @Ignore
    public Topic(@NonNull String topic, String desc) {
        this.mTopic = topic;
        this.description = desc;
    }

    public String getTopic() {
        return this.mTopic;
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


