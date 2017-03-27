package com.example.yass.remindme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yass.remindme.R;
import com.example.yass.remindme.db.SharedPreferencesHelper;
import com.example.yass.remindme.interfaces.RemindListAdapterCallBack;
import com.example.yass.remindme.interfaces.WordListAdapterCallBack;
import com.example.yass.remindme.models.UserAction;
import com.example.yass.remindme.models.Word;

import java.util.ArrayList;

/**
 * Created by yass on 2/20/17.
 */

public class WordsListAdapter extends RecyclerView.Adapter<WordsListAdapter.WordsViewHolder> {

    private ArrayList<Word> wordArrayList;
    private WordListAdapterCallBack callBack;
    private Context context;

    public WordsListAdapter(ArrayList<Word> wordArrayList, WordListAdapterCallBack callBack, Context context) {
        this.wordArrayList = wordArrayList;
        this.callBack = callBack;
        this.context = context;
    }

    @Override
    public WordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item, parent, false);
        return new WordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WordsViewHolder holder, int position) {
        holder.titleTextView.setText(wordArrayList.get(position).getTitle());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesHelper.getInstance().saveNewAction(new UserAction(context.getString(R.string.deleted),
                        context.getString(R.string.word)));
                wordArrayList.remove(holder.getAdapterPosition());
                SharedPreferencesHelper.getInstance().saveNewWords(wordArrayList);
                notifyDataSetChanged();
            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.renameWordCard(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return wordArrayList.size();
    }

    public static class WordsViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private ImageButton editButton;
        private ImageButton deleteButton;

        public WordsViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.title_word_textView);
            editButton = (ImageButton) itemView.findViewById(R.id.rename_word_title);
            deleteButton = (ImageButton) itemView.findViewById(R.id.delete_word);
        }
    }

    public void setData(ArrayList<Word> words) {
        wordArrayList = words;
        notifyDataSetChanged();
    }
}
