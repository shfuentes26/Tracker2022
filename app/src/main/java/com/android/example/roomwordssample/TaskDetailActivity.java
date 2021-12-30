package com.android.example.roomwordssample;

import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_DESC;
import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_ID;
import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_UPDATE_TOPIC;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetailActivity extends AppCompatActivity {

    public static final String EXTRA_DATA_ID = "extra_data_id";
    public static final String EXTRA_DATA_DESC = "extra_data_desc";
    public static final String EXTRA_DATA_NAME = "extra_data_name";

    private TextView mTaskNameTxt;
    private TextView mTaskDesTxt;
    private TopicViewModel mTopicViewModel;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        mTaskNameTxt = findViewById(R.id.task_name_txt);
        mTaskDesTxt = findViewById(R.id.task_desc_txt);

        final Bundle extras = getIntent().getExtras();
        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            id = extras.getInt(EXTRA_DATA_ID);
            String description = extras.getString(EXTRA_DATA_DESC, "");
            String task = extras.getString(EXTRA_DATA_NAME, "");
            if (!task.isEmpty()) {
                mTaskNameTxt.setText(task);
                mTaskDesTxt.setText(description);
            }
        }

    }
}