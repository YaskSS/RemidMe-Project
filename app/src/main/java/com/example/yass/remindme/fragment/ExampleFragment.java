package com.example.yass.remindme.fragment;

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

public class ExampleFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_example_layout;

    private View view;

    public static ExampleFragment getInstance(){
        Bundle bundle = new Bundle();
        ExampleFragment exampleFragment = new ExampleFragment();
        exampleFragment.setArguments(bundle);

        return exampleFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        return view;
    }
}
