package com.android.example.roomwordssample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.TopicViewHolder> {

    private final LayoutInflater mInflater;
    private List<Topic> mTopics; // Cached copy of topics
    private static TopicListAdapter.ClickListener clickListener;

    TopicListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TopicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopicViewHolder holder, int position) {
        if (mTopics != null) {
            Topic current = mTopics.get(position);
            holder.topicItemView.setText(current.getTopic());
        } else {
            // TODO: Change no_word tag
            //  Covers the case of data not being ready yet.
            holder.topicItemView.setText(R.string.no_word);
        }
    }

    /**
     * Associates a list of topics with this adapter
     */
    void setTopics(List<Topic> topics) {
        mTopics = topics;
        notifyDataSetChanged();
    }

    /**
     * getItemCount() is called many times, and when it is first called,
     * mTopics has not been updated (means initially, it's null, and we can't return null).
     */
    @Override
    public int getItemCount() {
        if (mTopics != null)
            return mTopics.size();
        else return 0;
    }

    /**
     * Gets the topic at a given position.
     * This method is useful for identifying which topic
     * was clicked or swiped in methods that handle user events.
     *
     * @param position The position of the topic in the RecyclerView
     * @return The topic at the given position
     */
    public Topic getTopicAtPosition(int position) {
        return mTopics.get(position);
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

    public void setOnItemClickListener(TopicListAdapter.ClickListener clickListener) {
        TopicListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }

}
