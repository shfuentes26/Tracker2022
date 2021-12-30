/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.example.roomwordssample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

/**
 * This class displays a list of words in a RecyclerView.
 * The words are saved in a Room database.
 * The layout for this activity also displays a FAB that
 * allows users to start the NewWordActivity to add new words.
 * Users can delete a word by swiping it away, or delete all words
 * through the Options menu.
 * Whenever a new word is added, deleted, or updated, the RecyclerView
 * showing the list of words automatically updates.
 */
public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 3;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 4;
    public static final int NEW_TOPIC_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_TOPIC_ACTIVITY_REQUEST_CODE = 2;


    public static final String EXTRA_DATA_UPDATE_TOPIC = "extra_topic_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";
    public static final String EXTRA_DATA_DESC = "extra_data_desc";

    //private WordViewModel mWordViewModel;
    private TopicViewModel mTopicViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the RecyclerView.
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        //final WordListAdapter adapter = new WordListAdapter(this);
        final TopicListAdapter adapter = new TopicListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the WordViewModel.
        mTopicViewModel = ViewModelProviders.of(this).get(TopicViewModel.class);
        // Get all the topics from the database
        // and associate them to the adapter.
        mTopicViewModel.getAllTopics().observe(this, new Observer<List<Topic>>() {
            @Override
            public void onChanged(@Nullable final List<Topic> topics) {
                // Update the cached copy of the words in the adapter.
                adapter.setTopics(topics);
            }
        });

        // Floating action button setup
        FloatingActionButton fab = findViewById(R.id.fab_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewTopicActivity.class);
                startActivityForResult(intent, NEW_TOPIC_ACTIVITY_REQUEST_CODE);
            }
        });

        // Add the functionality to swipe items in the
        // RecyclerView to delete the swiped item.
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    // We are not implementing onMove() in this app.
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    // When the use swipes a word,
                    // delete that word from the database.
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Topic myTopic = adapter.getTopicAtPosition(position);
                        Toast.makeText(MainActivity.this,
                                getString(R.string.delete_word_preamble) + " " +
                                        myTopic.getTopic(), Toast.LENGTH_LONG).show();

                        // Delete the word.
                        mTopicViewModel.deleteTopic(myTopic);
                    }
                });
        // Attach the item touch helper to the recycler view.
        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TopicListAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                Topic topic = adapter.getTopicAtPosition(position);
                launchUpdateTopicActivity(topic);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // The options menu has a single item "Clear all data now"
    // that deletes all the entries in the database.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, as long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, R.string.clear_data_toast_text, Toast.LENGTH_LONG).show();

            // Delete the existing data.
            mTopicViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * When the user enters a new word in the NewWordActivity,
     * that activity returns the result to this activity.
     * If the user entered a new word, save it in the database.

     * @param requestCode ID for the request
     * @param resultCode indicates success or failure
     * @param data The Intent sent back from the NewWordActivity,
     *             which includes the word that the user entered
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_TOPIC_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Topic topic = new Topic(data.getStringExtra(NewTopicActivity.EXTRA_REPLY), data.getStringExtra(NewTopicActivity.EXTRA_REPLY_DESC));
            // Save the data.
            mTopicViewModel.insert(topic);
        } else if (requestCode == UPDATE_TOPIC_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            String topic_data = data.getStringExtra(NewTopicActivity.EXTRA_REPLY);
            int id = data.getIntExtra(NewTopicActivity.EXTRA_REPLY_ID, -1);

            if (id != -1) {
                mTopicViewModel.update(new Topic(id, topic_data));
            } else {
                Toast.makeText(this, R.string.unable_to_update,
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(
                    this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }

    public void launchUpdateTopicActivity( Topic topic) {
        Intent intent = new Intent(this, TopicDetailActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_TOPIC, topic.getTopic());
        intent.putExtra(EXTRA_DATA_ID, topic.getId());
        intent.putExtra(EXTRA_DATA_DESC, topic.getDescription());
        startActivityForResult(intent, UPDATE_TOPIC_ACTIVITY_REQUEST_CODE);
    }
}