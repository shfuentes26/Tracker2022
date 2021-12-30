package com.android.example.roomwordssample;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class TopicWithTasks {
    @Embedded
    public Topic topic;
    @Relation(
            parentColumn = "topic",
            entityColumn = "topicId"
    )
    public List<Task> tasksList;
}
