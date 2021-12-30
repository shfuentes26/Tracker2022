package com.android.example.roomwordssample;

import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_ID;
import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_UPDATE_TOPIC;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewTaskActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_TASK = "com.example.android.roomwordssample.REPLY_TASK";
    public static final String EXTRA_REPLY_TASK_DESC = "com.example.android.roomwordssample.REPLY_TASK_DESC";
    public static final String EXTRA_REPLY_TASK_ID = "com.android.example.roomwordssample.REPLY_TASK_ID";
    public static final String EXTRA_DATA_TOPIC_ID = "extra_data_topic_id";

    private EditText mEditTaskView;
    private EditText mEditDescTaskView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        mEditTaskView = findViewById(R.id.edit_task);
        mEditDescTaskView = findViewById(R.id.edit_task_desc);

        int id = -1 ;

        final Bundle extras = getIntent().getExtras();
        //TODO: tendremos que obtener el topic id para asociar a task ese id
        if (extras != null) {
            id = extras.getInt(EXTRA_DATA_TOPIC_ID);
        }

        final Button button = findViewById(R.id.button_save_task);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Create a new Intent for the reply.
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditTaskView.getText())) {
                    // No word was entered, set the result accordingly.
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // Get the new topic that the user entered.
                    String task = mEditTaskView.getText().toString();
                    String desc = mEditDescTaskView.getText().toString();
                    // Put the new topic in the extras for the reply Intent.
                    replyIntent.putExtra(EXTRA_REPLY_TASK, task);
                    replyIntent.putExtra(EXTRA_REPLY_TASK_DESC, desc);
                    if (extras != null && extras.containsKey(EXTRA_DATA_ID)) {
                        int id = extras.getInt(EXTRA_DATA_ID, -1);
                        if (id != -1) {
                            replyIntent.putExtra(EXTRA_REPLY_TASK_ID, id);
                        }
                     }
                    // Set the result status to indicate success.
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

    }
}