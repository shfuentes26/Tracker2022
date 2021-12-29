package com.android.example.roomwordssample;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TopicRepository {

    private TopicDao mTopicDao;
    private TaskDao mTaskDao;
    private LiveData<List<Topic>> mAllTopics;
    private LiveData<List<Task>> mAllTasks;

    TopicRepository(Application application) {
        TopicRoomDatabase db = TopicRoomDatabase.getDatabase(application);
        mTopicDao = db.topicDao();
        mTaskDao = db.taskDao();
        mAllTopics = mTopicDao.getAllTopics();
        mAllTasks = mTaskDao.getAllTasks();
    }


    LiveData<List<Task>>getAllTasks(){
        return mAllTasks;
    }

    LiveData<List<Topic>>getAllTopics(){
        return mAllTopics;
    }

    /** Added method for getting a single topic **/
    public void getTopic(int id) {
        new TopicRepository.getTopicAsyncTask(mTopicDao).execute(id);
    }

    public void insertTopic(Topic topic) {
        new TopicRepository.insertTopicAsyncTask(mTopicDao).execute(topic);
    }

    public void insertTask(Task task) {
        new TopicRepository.insertTaskAsyncTask(mTaskDao).execute(task);
    }

    public void update(Topic topic)  {
        new TopicRepository.updateTopicAsyncTopic(mTopicDao).execute(topic);
    }

    public void updateTask(Task task)  {
        new TopicRepository.updateTaskAsyncTask(mTaskDao).execute(task);
    }

    public void deleteAll()  {
        new TopicRepository.deleteAllTopicsAsyncTask(mTopicDao).execute();
    }

    // Must run off main thread
    public void deleteTopic(Topic topic) {
        new TopicRepository.deleteTopicAsyncTask(mTopicDao).execute(topic);
    }

    // Must run off main thread
    public void deleteTask(Task task) {
        new TopicRepository.deleteTaskAsyncTask(mTaskDao).execute(task);
    }

    // Static inner classes below here to run database interactions in the background.

    private static class getTopicAsyncTask extends AsyncTask<Topic, Void, Void> {
        private TopicDao mAsyncTaskDao;

        getTopicAsyncTask(TopicDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Topic... params) {
            Topic topic = params[0];
            mAsyncTaskDao.getTopic(topic.getId());
            return null;
        }

        public void execute(int id) {
            mAsyncTaskDao.getTopic(id);
        }
    }


    /**
     * Inserts a topic into the database.
     */
    private static class insertTopicAsyncTask extends AsyncTask<Topic, Void, Void> {

        private TopicDao mAsyncDao;

        insertTopicAsyncTask(TopicDao dao) {
            mAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(final Topic... params) {
            mAsyncDao.insert(params[0]);
            return null;
        }
    }

    private static class insertTaskAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao mAsyncTaskDao;

        insertTaskAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     * Deletes all topics from the database (does not delete the table).
     */
    private static class deleteAllTopicsAsyncTask extends AsyncTask<Void, Void, Void> {
        private TopicDao mAsyncTaskDao;
        private TaskDao mAsyncTaskTaskDao;

        deleteAllTopicsAsyncTask(TopicDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteAllTasksAsyncTask extends AsyncTask<Void, Void, Void> {

        private TaskDao mAsyncTaskTaskDao;

        deleteAllTasksAsyncTask(TaskDao dao) {
            mAsyncTaskTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskTaskDao.deleteAll();
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
     *  Deletes a single topic from the database.
     */
    private static class deleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mAsyncTaskDao;

        deleteTaskAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.deleteTask(params[0]);
            return null;
        }
    }

    /**
     *  Updates a topic in the database.
     */
    private static class updateTopicAsyncTopic extends AsyncTask<Topic, Void, Void> {
        private TopicDao mAsyncTopicDao;

        updateTopicAsyncTopic(TopicDao dao) {
            mAsyncTopicDao = dao;
        }

        @Override
        protected Void doInBackground(final Topic... params) {
            mAsyncTopicDao.update(params[0]);
            return null;
        }
    }

    private static class updateTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mAsyncTaskDao;

        updateTaskAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
