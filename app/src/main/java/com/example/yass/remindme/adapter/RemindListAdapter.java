package com.example.yass.remindme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yass.remindme.R;
import com.example.yass.remindme.db.SharedPreferencesHelper;
import com.example.yass.remindme.interfaces.RemindListAdapterCallBack;
import com.example.yass.remindme.models.Remind;
import com.example.yass.remindme.models.UserAction;

import java.util.ArrayList;

/**
 * Created by yass on 2/20/17.
 */

public class RemindListAdapter extends RecyclerView.Adapter<RemindListAdapter.RemindViewHolder> {

    private ArrayList<Remind> remindArrayList;
    private RemindListAdapterCallBack callBack;
    private Context context;

    public RemindListAdapter(ArrayList<Remind> remindArrayList, RemindListAdapterCallBack callBack, Context context) {
        this.remindArrayList = remindArrayList;
        this.callBack = callBack;
        this.context = context;
    }

    @Override
    public RemindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remind_item, parent, false);
        return new RemindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RemindViewHolder holder, final int position) {
        holder.titleTextView.setText(remindArrayList.get(position).getType());
        holder.bodyTextView.setText(remindArrayList.get(position).getNote());

        holder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesHelper.getInstance().saveNewAction(new UserAction(context.getString(R.string.remind) ,context.getString(R.string.deleted)
                        ));
                remindArrayList.remove(holder.getAdapterPosition());
                SharedPreferencesHelper.getInstance().saveNewRemind(remindArrayList);
                notifyDataSetChanged();
            }
        });

        holder.editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.editRemindCard(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return remindArrayList.size();
    }

    public static class RemindViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView bodyTextView;
        private ImageButton deleteImageButton;
        private ImageButton editImageButton;

        public RemindViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.title_remind_textView);
            bodyTextView = (TextView) itemView.findViewById(R.id.body_remind_textView);

            deleteImageButton = (ImageButton) itemView.findViewById(R.id.delete_remind);
            editImageButton = (ImageButton) itemView.findViewById(R.id.rename_remind);
        }
    }

    public void setData(ArrayList<Remind> remindArrayList) {
        this.remindArrayList = remindArrayList;
        notifyDataSetChanged();
    }
}
