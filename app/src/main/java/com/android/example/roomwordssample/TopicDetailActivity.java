package com.android.example.roomwordssample;

import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_UPDATE_TOPIC;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TopicDetailActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.roomwordssample.REPLY";
    public static final String EXTRA_REPLY_DESC = "com.example.android.roomwordssample.REPLY_DESC";
    public static final String EXTRA_REPLY_ID = "com.android.example.roomwordssample.REPLY_ID";
    public static final int NEW_TASK_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_TASK_ACTIVITY_REQUEST_CODE = 2;
    public static final int DETAIL_TASK_ACTIVITY_REQUEST_CODE = 3;

    public static final String EXTRA_DATA_ID = "extra_data_id";
    public static final String EXTRA_DATA_DESC = "extra_data_desc";
    public static final String EXTRA_DATA_NAME = "extra_data_name";
    public static final String EXTRA_DATA_TOPIC_ID = "extra_data_topic_id";

    private TextView mTopicNameTxt;
    private TextView mTopicDesTxt;
    private TopicViewModel mTopicViewModel;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        mTopicNameTxt = findViewById(R.id.topic_name_txt);
        mTopicDesTxt = findViewById(R.id.topic_desc_txt);

        RecyclerView recyclerView = findViewById(R.id.task_recycler);
        final TaskListAdapter adapter = new TaskListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        final Bundle extras = getIntent().getExtras();
        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            id = extras.getInt(EXTRA_DATA_ID);
            String description = extras.getString(EXTRA_DATA_DESC);
            String topic = extras.getString(EXTRA_DATA_UPDATE_TOPIC, "");
            if (!topic.isEmpty()) {
                mTopicNameTxt.setText(topic);
                mTopicDesTxt.setText(description);
            }
        }
        //button to create a task for the topic, it redirects to the NewTaskActivity
        final Button button = findViewById(R.id.button_add_task);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(TopicDetailActivity.this,
                       "Creating new tasks...", Toast.LENGTH_LONG).show();
                launchCreateTaskActivity(id);
            }
        });


        // Set up the WordViewModel.
        mTopicViewModel = ViewModelProviders.of(this).get(TopicViewModel.class);
        // Get all the task from the database with the related topicId
        // and associate them to the adapter.
        mTopicViewModel.getAllTasks(id).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> tasks) {
                // Update the cached copy of the words in the adapter.
                adapter.setTasks(tasks);
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
                        Task myTask = adapter.getTaskAtPosition(position);
                        Toast.makeText(TopicDetailActivity.this,
                                getString(R.string.delete_word_preamble) + " " +
                                        myTask.getTask(), Toast.LENGTH_LONG).show();

                        // Delete the word.
                        mTopicViewModel.deleteTask(myTask);
                    }
                });
        // Attach the item touch helper to the recycler view.
        helper.attachToRecyclerView(recyclerView);


        adapter.setOnItemClickListener(new TaskListAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                Task task = adapter.getTaskAtPosition(position);
                launchDetailTaskActivity(task);
            }
        });



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

        if (requestCode == NEW_TASK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Task task = new Task(data.getStringExtra(NewTaskActivity.EXTRA_REPLY_TASK),data.getStringExtra(NewTaskActivity.EXTRA_REPLY_TASK_DESC), id);
            // Save the data.
            mTopicViewModel.insertTask(task);
        } else if (requestCode == UPDATE_TASK_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            String task_data = data.getStringExtra(NewTaskActivity.EXTRA_REPLY_TASK);
            int id = data.getIntExtra(NewTaskActivity.EXTRA_REPLY_TASK_ID, -1);

            if (id != -1) {
                mTopicViewModel.updateTask(new Task(id, task_data));
            } else {
                Toast.makeText(this, R.string.unable_to_update,
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(
                    this, "Task not saved", Toast.LENGTH_LONG).show();
        }
    }

    public void launchCreateTaskActivity( int topicId) {
        Intent intent = new Intent(this, NewTaskActivity.class);
        intent.putExtra(EXTRA_DATA_TOPIC_ID, topicId);
        startActivityForResult(intent, NEW_TASK_ACTIVITY_REQUEST_CODE);
    }

    public void launchDetailTaskActivity( Task task) {
        Intent intent = new Intent(this, TaskDetailActivity.class);
        intent.putExtra(EXTRA_DATA_ID, task.getId());
        intent.putExtra(EXTRA_DATA_DESC, task.getDescription());
        intent.putExtra(EXTRA_DATA_NAME, task.getTask());
        startActivityForResult(intent, DETAIL_TASK_ACTIVITY_REQUEST_CODE);
    }
}