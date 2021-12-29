package com.android.example.roomwordssample;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class TopicViewModel extends AndroidViewModel {

    private TopicRepository mRepository;

    private LiveData<List<Topic>> mAllTopics;

    public TopicViewModel(Application application) {
        super(application);
        mRepository = new TopicRepository(application);
        mAllTopics = mRepository.getAllTopics();
    }

    LiveData<List<Topic>> getAllTopics() {
        return mAllTopics;
    }

    public void insert(Topic topic) {
        mRepository.insert(topic);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteTopic(Topic topic) {
        mRepository.deleteTopic(topic);
    }

    public void update(Topic topic) {
        mRepository.update(topic);
    }
}
