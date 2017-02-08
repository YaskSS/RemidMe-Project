package com.example.yass.remindme.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yass.remindme.R;
import com.example.yass.remindme.adapter.RemindListAdapter;
import com.example.yass.remindme.dto.RemindDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yass on 1/29/17.
 */

public class HistoryFragment extends AbstractTabFragment {

    private static final int LAYOUT = R.layout.fragment_history;

    private List<RemindDto> data;
    private RemindListAdapter remindListAdapter;

    public static HistoryFragment getInstance(Context context, List<RemindDto> data){
        Bundle bundle = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(bundle);
        fragment.setData(data);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_history));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        remindListAdapter = new RemindListAdapter(data);
        recyclerView.setAdapter(remindListAdapter);

        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setData(List<RemindDto> data) {
        this.data = data;
    }

    public void refreshData(List<RemindDto> list){
        remindListAdapter.setData(list);
        remindListAdapter.notifyDataSetChanged();
    }
}
