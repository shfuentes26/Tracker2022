package com.android.example.roomwordssample;

import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_UPDATE_TOPIC;
import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_ID;
import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_DESC;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TopicDetailActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.roomwordssample.REPLY";
    public static final String EXTRA_REPLY_DESC = "com.example.android.roomwordssample.REPLY_DESC";
    public static final String EXTRA_REPLY_ID = "com.android.example.roomwordssample.REPLY_ID";
    public static final int CREATE_TASK_ACTIVITY_REQUEST_CODE = 1;

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

        //TODO: Obtener referencia de recyclerview
        /**mTopicViewModel.getTopic().observe(this, new Observer<List<Topic>>() {
            @Override
            public void onChanged(@Nullable final List<Topic> topics) {
                // Update the cached copy of the words in the adapter.
                //adapter.setTopics(topics);
            }
        });**/



    }

    public void launchCreateTaskActivity( int topicId) {
        Intent intent = new Intent(this, NewTaskActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_TOPIC, topicId);
        startActivityForResult(intent, CREATE_TASK_ACTIVITY_REQUEST_CODE);
    }
}