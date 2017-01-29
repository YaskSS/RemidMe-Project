package com.example.yass.remindme.navigationDrawer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yass.remindme.R;

import java.util.List;

/**
 * Created by yass on 1/28/17.
 */

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerViewHolder>{

    private List<NavigationItem> data;
    private View selectedView;
    private int selectionPosition;

    public NavigationDrawerAdapter(List<NavigationItem> data) {
        this.data = data;
    }

    private NavigationDrawablewCallbacks callBacks;

    @Override
    public NavigationDrawerViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_row, parent, false);

        final NavigationDrawerViewHolder viewHolder = new NavigationDrawerViewHolder(view);
        viewHolder.itemView.setClickable(true);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedView != null){
                    selectedView.setSelected(false);
                }

                selectionPosition = viewHolder.getAdapterPosition();

                if (callBacks != null){
                    callBacks.onNavigationDrawableSelected(viewHolder.getAdapterPosition());
                }
            }
        });

        viewHolder.itemView.setBackgroundResource(R.drawable.row_selector);

        return null;
    }

    @Override
    public void onBindViewHolder(NavigationDrawerViewHolder holder, int position) {
        holder.getTextView().setText(data.get(position).getString());
        holder.getTextView().setCompoundDrawablesWithIntrinsicBounds(data.get(position).getDrawable(),null, null, null);

        if (selectionPosition == position){
            if (selectedView != null){
                selectedView.setSelected(false);
            }

            selectionPosition = position;
            selectedView = holder.itemView;
            selectedView.setSelected(true);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}
