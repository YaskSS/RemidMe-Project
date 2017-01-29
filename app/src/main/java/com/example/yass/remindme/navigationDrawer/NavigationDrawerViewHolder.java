package com.example.yass.remindme.navigationDrawer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yass on 1/28/17.
 */

public class NavigationDrawerViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public NavigationDrawerViewHolder(View itemView) {
        super(itemView);
    }
}
