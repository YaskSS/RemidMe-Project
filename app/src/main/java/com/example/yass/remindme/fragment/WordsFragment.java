package com.example.yass.remindme.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.yass.remindme.R;
import com.example.yass.remindme.adapter.WordsListAdapter;
import com.example.yass.remindme.db.SharedPreferencesHelper;
import com.example.yass.remindme.interfaces.RemindListAdapterCallBack;
import com.example.yass.remindme.interfaces.WordListAdapterCallBack;
import com.example.yass.remindme.models.UserAction;
import com.example.yass.remindme.models.Word;

import java.util.ArrayList;

/**
 * Created by yass on 1/29/17.
 */

public class WordsFragment extends AbstractTabFragment implements WordListAdapterCallBack {

    private static final int LAYOUT = R.layout.fragment_words;
    private RecyclerView recyclerView;
    private WordsListAdapter wordsListAdapter;
    private ArrayList<Word> words;
    private FloatingActionButton fab;

    public static WordsFragment getInstance(Context context) {
        Bundle bundle = new Bundle();
        WordsFragment fragment = new WordsFragment();
        fragment.setArguments(bundle);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_words));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        words = SharedPreferencesHelper.getInstance().getWords();
        initFab();
        initAdapters();
        return view;
    }

    private void initFab() {
        fab = (FloatingActionButton) view.findViewById(R.id.word_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(false,null);
            }
        });
    }

    private void initAdapters() {
        wordsListAdapter = new WordsListAdapter(words, this, context);

        recyclerView = (RecyclerView) view.findViewById(R.id.word_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(wordsListAdapter);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void renameWordCard(int position) {
        words = SharedPreferencesHelper.getInstance().getWords();
        showDialog(true, words.get(position));
        words.remove(position);
    }

    private void showDialog(boolean rename, final Word word) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        if (rename == false) {
            builder.title(getString(R.string.new_word));
            builder.inputType(InputType.TYPE_CLASS_TEXT);
            builder.input(R.string.word_theme, 0, new MaterialDialog.InputCallback() {
                @Override
                public void onInput(MaterialDialog dialog, CharSequence input) {
                    saveAction(context.getString(R.string.created), context.getString(R.string.word));
                    SharedPreferencesHelper.getInstance().saveNewWord(new Word(input.toString()));
                    wordsListAdapter.setData(SharedPreferencesHelper.getInstance().getWords());}
            });
        } else {
            builder.title(word.getTitle());
            builder.inputType(InputType.TYPE_CLASS_TEXT);
            builder.input(R.string.new_name_word, 0, new MaterialDialog.InputCallback() {
                @Override
                public void onInput(MaterialDialog dialog, CharSequence input) {
                    if (input.equals("")){
                        words.add(word);
                        saveAction(context.getString(R.string.refactored),context.getString(R.string.word));
                    }else {
                       words.add(new Word(input.toString()));
                        saveAction(context.getString(R.string.refactored), context.getString(R.string.word));
                    }
                    SharedPreferencesHelper.getInstance().saveNewWords(words);
                    wordsListAdapter.setData(SharedPreferencesHelper.getInstance().getWords());
                }
            });
        }
        builder.show();
    }

    private void saveAction(String title, String body){
        SharedPreferencesHelper.getInstance().saveNewAction(new UserAction(title, body));
    }
}
