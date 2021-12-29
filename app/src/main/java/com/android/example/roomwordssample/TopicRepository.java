package com.android.example.roomwordssample;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TopicRepository {

    private TopicDao mTopicDao;
    private LiveData<List<Topic>> mAllTopics;

    TopicRepository(Application application) {
        TopicRoomDatabase db = TopicRoomDatabase.getDatabase(application);
        mTopicDao = db.topicDao();
        mAllTopics = mTopicDao.getAllTopics();
    }

    LiveData<List<Topic>>getAllTopics(){
        return mAllTopics;
    }

    public void insert(Topic topic) {
        new TopicRepository.insertAsyncTask(mTopicDao).execute(topic);
    }

    public void update(Topic topic)  {
        new TopicRepository.updateTopicAsyncTask(mTopicDao).execute(topic);
    }

    public void deleteAll()  {
        new TopicRepository.deleteAllTopicsAsyncTask(mTopicDao).execute();
    }

    // Must run off main thread
    public void deleteTopic(Topic topic) {
        new TopicRepository.deleteTopicAsyncTask(mTopicDao).execute(topic);
    }

    // Static inner classes below here to run database interactions in the background.

    /**
     * Inserts a topic into the database.
     */
    private static class insertAsyncTask extends AsyncTask<Topic, Void, Void> {

        private TopicDao mAsyncTaskDao;

        insertAsyncTask(TopicDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Topic... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     * Deletes all topics from the database (does not delete the table).
     */
    private static class deleteAllTopicsAsyncTask extends AsyncTask<Void, Void, Void> {
        private TopicDao mAsyncTaskDao;

        deleteAllTopicsAsyncTask(TopicDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    /**
     *  Deletes a single topic from the database.
     */
    private static class deleteTopicAsyncTask extends AsyncTask<Topic, Void, Void> {
        private TopicDao mAsyncTaskDao;

        deleteTopicAsyncTask(TopicDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Topic... params) {
            mAsyncTaskDao.deleteTopic(params[0]);
            return null;
        }
    }

    /**
     *  Updates a topic in the database.
     */
    private static class updateTopicAsyncTask extends AsyncTask<Topic, Void, Void> {
        private TopicDao mAsyncTaskDao;

        updateTopicAsyncTask(TopicDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Topic... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
