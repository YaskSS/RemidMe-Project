package com.example.yass.remindme.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yass.remindme.R;
import com.example.yass.remindme.models.UserAction;

import java.util.List;

/**
 * Created by yass on 1/31/17.
 */

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder> {

    private List<UserAction> data;

    public HistoryListAdapter(List<UserAction> data) {
        this.data = data;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        holder.title.setText(data.get(position).getAction());
        holder.date.setText(data.get(position).getDate());
        holder.body.setText(data.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView title;
        private TextView date;
        private TextView body;

        public HistoryViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardView);
            title = (TextView) itemView.findViewById(R.id.title_textView);
            date = (TextView) itemView.findViewById(R.id.date);
            body = (TextView) itemView.findViewById(R.id.body);
        }
    }

    public void setData(List<UserAction> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
