package com.example.yass.remindme.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yass.remindme.R;

/**
 * Created by yass on 1/29/17.
 */

public class IdeasFragment extends AbstractTabFragment {

    private static final int LAYOUT = R.layout.fragment_example_layout;

    public static IdeasFragment getInstance(Context context){
        Bundle bundle = new Bundle();
        IdeasFragment fragment = new IdeasFragment();
        fragment.setArguments(bundle);
        fragment.setArguments(bundle);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_ideas));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
