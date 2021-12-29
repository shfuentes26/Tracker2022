package com.android.example.roomwordssample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TopicViewHolder> {

    private final LayoutInflater mInflater;
    private List<Task> mTasks; // Cached copy of tasks
    private static TaskListAdapter.ClickListener clickListener;

    TaskListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public TaskListAdapter.TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TaskListAdapter.TopicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskListAdapter.TopicViewHolder holder, int position) {
        if (mTasks != null) {
            Task current = mTasks.get(position);
            holder.topicItemView.setText(current.getTask());
        } else {
            // TODO: Change no_word tag
            //  Covers the case of data not being ready yet.
            holder.topicItemView.setText(R.string.no_word);
        }
    }

    /**
     * Associates a list of topics with this adapter
     */
    void setTasks(List<Task> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }

    /**
     * getItemCount() is called many times, and when it is first called,
     * mTopics has not been updated (means initially, it's null, and we can't return null).
     */
    @Override
    public int getItemCount() {
        if (mTasks != null)
            return mTasks.size();
        else return 0;
    }


    /**
     * Gets the task at a given position.
     * This method is useful for identifying which topic
     * was clicked or swiped in methods that handle user events.
     *
     * @param position The position of the topic in the RecyclerView
     * @return The topic at the given position
     */
    public Task getTaskAtPosition(int position) {
        return mTasks.get(position);
    }

    class TopicViewHolder extends RecyclerView.ViewHolder {
        private final TextView topicItemView;

        private TopicViewHolder(View itemView) {
            super(itemView);
            topicItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(TaskListAdapter.ClickListener clickListener) {
        TaskListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }

}
