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
import com.example.yass.remindme.fragment.ToDoFragment;
import com.example.yass.remindme.interfaces.ToDoListAdapterCallBack;
import com.example.yass.remindme.models.ToDo;
import com.example.yass.remindme.models.UserAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yass on 2/20/17.
 */

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.TodoViewHolder> {

    private ArrayList<ToDo> toDoList;
    private ToDoListAdapterCallBack toDoListAdapterCallBack;
    private Context context;

    public ToDoListAdapter(ArrayList<ToDo> toDoList, ToDoListAdapterCallBack toDoListAdapterCallBack, Context context) {
        this.toDoList = toDoList;
        this.toDoListAdapterCallBack = toDoListAdapterCallBack;
        this.context = context;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TodoViewHolder holder, final int position) {
        holder.titleTextView.setText(toDoList.get(position).getType());
        holder.bodyTextView.setText(toDoList.get(position).getBody());

        holder.renameImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDoListAdapterCallBack.editToDoCard(holder.getAdapterPosition());
            }
        });

        holder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesHelper.getInstance().saveNewAction(new UserAction(context.getString(R.string.action),
                        context.getString(R.string.deleted)));
                toDoList.remove(holder.getAdapterPosition());
                SharedPreferencesHelper.getInstance().saveNewToDo(toDoList);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }


    public static class TodoViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView bodyTextView;
        private ImageButton renameImageButton;
        private ImageButton deleteImageButton;

        public TodoViewHolder(View view) {
            super(view);

            titleTextView = (TextView) view.findViewById(R.id.title_todo_textView);
            bodyTextView = (TextView) view.findViewById(R.id.body_todo_textView);
            renameImageButton = (ImageButton) view.findViewById(R.id.rename_todo_button);
            deleteImageButton = (ImageButton) view.findViewById(R.id.delete_todo_button);
        }
    }

    public void setDataToDoListAdapter(ArrayList<ToDo> toDoList) {
        this.toDoList = toDoList;
        notifyDataSetChanged();
    }
}
