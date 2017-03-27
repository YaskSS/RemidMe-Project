package com.example.yass.remindme;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.yass.remindme.adapter.TabsFragmentAdapter;

/**
 * Created by yass on 1/28/17.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int LAYOUT = R.layout.activity_main;
    private static final String TAG = "MainActivity";

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private static Context context;

    private TabsFragmentAdapter adapter;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        context = base;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Default);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initStatusBar();
        initToolbar();
        initTabs();
        initNavigationView();
    }

    private void initStatusBar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        toolbar.inflateMenu(R.menu.menu);
    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.view_navigation_open, R.string.view_navigation_close);

        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new TabsFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // new RemindTask().execute();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawers();
        int id = item.getItemId();
        adapter.notifyDataSetChanged();
        switch (id) {
            case R.id.actionHistoryItem:
                viewPager.setCurrentItem(Constants.TAB_ONE);
                Log.i(TAG, String.valueOf(viewPager.getCurrentItem()));
                return true;
            case R.id.actionNotificationItem:
                viewPager.setCurrentItem(Constants.TAB_TWO);
                Log.i(TAG, String.valueOf(viewPager.getCurrentItem()));
                return true;
            case R.id.actionToDoItem:
                viewPager.setCurrentItem(Constants.TAB_THREE);
                Log.i(TAG, String.valueOf(viewPager.getCurrentItem()));
                return true;
            case R.id.actionWordsItem:
                viewPager.setCurrentItem(Constants.TAB_FOUR);
                Log.i(TAG, String.valueOf(viewPager.getCurrentItem()));
                return true;
        }
        return false;
    }

   /* private class RemindTask extends AsyncTask<Void, Void, UserAction>{

        @Override
        protected UserAction doInBackground(Void... voids) {

            RestTemplate template = new RestTemplate();
            template.getMessageConverters().add(new  MappingJackson2HttpMessageConverter());
            return template.getForObject(Constants.URL.GER_REMIND_ITEM, UserAction.class);
        }

        @Override
        protected void onPostExecute(UserAction remindDto) {
            List<UserAction> list = new ArrayList<>();
            list.add(remindDto);
            adapter.setData(list);
        }
    }*/

    public static Context getContext() {
        return context;
    }
}
