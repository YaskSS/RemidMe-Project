package com.example.yass.remindme;

import android.app.Application;

/**
 * Created by yass on 1/30/17.
 */

public class Constants extends Application {

    public static final int TAB_ONE = 0;
    public static final int TAB_TWO = 1;
    public static final int TAB_THREE = 2;
    public static final int TAB_FOUR = 3;
    private  String[] dataString = { getApplicationContext().getResources().getString(R.string.read_remind),getApplicationContext().getResources().getString(R.string.write_remind)};

}
