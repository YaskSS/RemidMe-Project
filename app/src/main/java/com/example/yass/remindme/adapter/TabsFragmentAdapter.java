package com.example.yass.remindme.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yass.remindme.R;
import com.example.yass.remindme.dto.RemindDto;
import com.example.yass.remindme.fragment.AbstractTabFragment;
import com.example.yass.remindme.fragment.BirthdayFragment;
import com.example.yass.remindme.fragment.HistoryFragment;
import com.example.yass.remindme.fragment.IdeasFragment;
import com.example.yass.remindme.fragment.ToD0Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yass on 1/29/17.
 */

public class TabsFragmentAdapter extends FragmentPagerAdapter {

    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;

    private List<RemindDto> data;
    private HistoryFragment historyFragment;

    public TabsFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);

        this.context = context;
        this.data = new ArrayList<>();
        initTabsMap(context);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {

        return tabs.get(position);

    }


    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabsMap(Context context) {
        tabs = new HashMap<>();

        historyFragment = HistoryFragment.getInstance (context, data);

        tabs.put(0, historyFragment);
        tabs.put(1, IdeasFragment.getInstance(context));
        tabs.put(2, ToD0Fragment.getInstance(context));
        tabs.put(3, BirthdayFragment.getInstance(context));
    }

    public void setData(List<RemindDto> data) {
        this.data = data;
        historyFragment.refreshData(data);
    }
}
