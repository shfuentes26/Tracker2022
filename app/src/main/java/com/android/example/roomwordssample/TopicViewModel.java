package com.android.example.roomwordssample;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class TopicViewModel extends AndroidViewModel {

    private TopicRepository mRepository;

    private LiveData<List<Topic>> mAllTopics;
    private LiveData<List<Task>> mAllTasks;

    public TopicViewModel(Application application) {
        super(application);
        mRepository = new TopicRepository(application);
        mAllTopics = mRepository.getAllTopics();
        //mAllTasks = mRepository.getAllTasks(0);
    }

    LiveData<List<Task>> getAllTasks(int topicId) {
        mAllTasks = mRepository.getAllTasks(topicId);
        return mAllTasks;
    }

    LiveData<List<Topic>> getAllTopics() {
        return mAllTopics;
    }

    public void insert(Topic topic) {
        mRepository.insertTopic(topic);
    }

    public void insertTask(Task task) {
        mRepository.insertTask(task);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteTopic(Topic topic) {
        mRepository.deleteTopic(topic);
    }

    public void deleteTask(Task task) {
        mRepository.deleteTask(task);
    }

    public void update(Topic topic) {
        mRepository.update(topic);
    }

    public void updateTask(Task task) {
        mRepository.updateTask(task);
    }

    public void getTopic(Topic topic) {
        mRepository.getTopic(topic.getId());
    }
}
