package com.android.example.roomwordssample;

import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_UPDATE_TOPIC;
import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_ID;
import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_DESC;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class TopicDetailActivity extends AppCompatActivity {

    private TextView mTopicNameTxt;
    private TextView mTopicDesTxt;
    private TopicViewModel mTopicViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        mTopicNameTxt = findViewById(R.id.topic_name_txt);
        mTopicDesTxt = findViewById(R.id.topic_desc_txt);

        final Bundle extras = getIntent().getExtras();
        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            Integer id = extras.getInt(EXTRA_DATA_ID);
            String description = extras.getString(EXTRA_DATA_DESC);
            String topic = extras.getString(EXTRA_DATA_UPDATE_TOPIC, "");
            if (!topic.isEmpty()) {
                mTopicNameTxt.setText(topic);
                mTopicDesTxt.setText(description);
            }
        }




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
}