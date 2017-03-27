package com.example.yass.remindme.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yass.remindme.R;
import com.example.yass.remindme.adapter.HistoryListAdapter;
import com.example.yass.remindme.db.SharedPreferencesHelper;
import com.example.yass.remindme.models.UserAction;

import java.util.List;

/**
 * Created by yass on 1/29/17.
 */

public class HistoryFragment extends AbstractTabFragment {

    private static final int LAYOUT = R.layout.fragment_history;

    private List<UserAction> data;
    private HistoryListAdapter historyListAdapter;
    private FloatingActionButton fab;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;

    public static HistoryFragment getInstance(Context context, List<UserAction> data){
        Bundle bundle = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(bundle);
        fragment.setData(data);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_history));
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(SharedPreferencesHelper.APP_PREFERENCES, 0);
        sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if (sharedPreferences.getString(SharedPreferencesHelper.HISTORY, null) != null){
                    historyListAdapter.setData(SharedPreferencesHelper.getInstance().getListUserActions());
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        initFab();
        setData(SharedPreferencesHelper.getInstance().getListUserActions());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        historyListAdapter = new HistoryListAdapter(data);
        recyclerView.setAdapter(historyListAdapter);

        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
        return view;
    }

    private void initFab() {
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_delete_forever_white_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesHelper.getInstance().deleteAllActions();
                refreshData();
            }
        });
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setData(List<UserAction> data) {
        this.data = data;
    }

    public void refreshData(){
       historyListAdapter.setData(SharedPreferencesHelper.getInstance().getListUserActions());
        historyListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }
}
