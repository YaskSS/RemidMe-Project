package com.example.yass.remindme.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yass.remindme.db.SharedPreferencesHelper;
import com.example.yass.remindme.models.Remind;
import com.example.yass.remindme.models.ToDo;
import com.example.yass.remindme.models.UserAction;
import com.example.yass.remindme.fragment.AbstractTabFragment;
import com.example.yass.remindme.fragment.WordsFragment;
import com.example.yass.remindme.fragment.HistoryFragment;
import com.example.yass.remindme.fragment.NotificationFragment;
import com.example.yass.remindme.fragment.ToDoFragment;

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

    private List<UserAction> data;
    private ArrayList<Remind> reminds;
    private ArrayList<ToDo> toDos;
    private HistoryFragment historyFragment;
    private NotificationFragment notificationFragment;
    private ToDoFragment toDoFragment;

    public TabsFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        /*ArrayList<ToDo> rsdgsdg = new ArrayList<ToDo>();
        rsdgsdg.add(new ToDo("",""));
        SharedPreferencesHelper.getInstance().saveNewToDo(rsdgsdg);*/
        this.context = context;
        this.data = SharedPreferencesHelper.getInstance().getListUserActions();
        this.reminds = SharedPreferencesHelper.getInstance().getListRemind();
        this.toDos = SharedPreferencesHelper.getInstance().getToDos();
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
        notificationFragment = NotificationFragment.getInstance(context, reminds);
        toDoFragment = ToDoFragment.getInstance(context, toDos);
        tabs.put(0, historyFragment);
        tabs.put(1, notificationFragment);
        tabs.put(2, toDoFragment);
        tabs.put(3, WordsFragment.getInstance(context));
    }

    public void setData(List<UserAction> data) {
        this.data = data;

    }
}
