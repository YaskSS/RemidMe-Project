package com.example.yass.remindme.navigationDrawer;

import android.graphics.drawable.Drawable;

/**
 * Created by yass on 1/28/17.
 */

public class NavigationItem {

    private String string;
    private Drawable drawable;

    public NavigationItem(String string, Drawable drawable) {
        this.string = string;
        this.drawable = drawable;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
