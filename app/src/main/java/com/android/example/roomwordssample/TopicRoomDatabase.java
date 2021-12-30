package com.android.example.roomwordssample;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Topic.class, Task.class}, version = 4, exportSchema = false)
public abstract class TopicRoomDatabase extends RoomDatabase {

    public abstract TopicDao topicDao();
    public abstract TaskDao taskDao();

    private static TopicRoomDatabase INSTANCE;

    public static TopicRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TopicRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TopicRoomDatabase.class, "topic_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // This callback is called when the database has opened.
    // In this case, use PopulateDbAsync to populate the database
    // with the initial data set if the database has no entries.
    private static RoomDatabase.Callback sRoomDatabaseCallback =  new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new TopicRoomDatabase.PopulateDbAsync(INSTANCE).execute();
        }
    };
    // Populate the database with the initial data set
    // only if the database has no entries.
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TopicDao mDao;
        private final TaskDao mTaskDao;

        // Initial data set
        private static String [] topics = {"Java", "Android", "iOS", "Algorithms", "AWS",
                "Kotlin", "Swift"};
        private static String [] tasks = {"Task1", "Task2",};

        PopulateDbAsync(TopicRoomDatabase db) {
            mDao = db.topicDao();
            mTaskDao = db.taskDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // If we have no topics, then create the initial list of topics.
            if (mDao.getAnyTopic().length < 1) {
                for (int i = 0; i <= topics.length - 1; i++) {
                    Topic topic = new Topic(topics[i]);
                    mDao.insert(topic);
                }
            }
            if (mTaskDao.getAnyTask().length < 1) {
                for (int i = 0; i <= tasks.length - 1; i++) {
                    Task task = new Task(tasks[i], "carga inicial", 1);
                    mTaskDao.insert(task);
                }
            }
            return null;
        }
    }

}
