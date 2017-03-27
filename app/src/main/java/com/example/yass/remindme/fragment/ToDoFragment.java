package com.example.yass.remindme.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.yass.remindme.R;
import com.example.yass.remindme.adapter.ToDoListAdapter;
import com.example.yass.remindme.db.SharedPreferencesHelper;
import com.example.yass.remindme.interfaces.ToDoListAdapterCallBack;
import com.example.yass.remindme.models.ToDo;
import com.example.yass.remindme.models.UserAction;

import java.util.ArrayList;

/**
 * Created by yass on 1/29/17.
 */

public class ToDoFragment extends AbstractTabFragment implements ToDoListAdapterCallBack {

    private static final int LAYOUT = R.layout.fragment_todo;

    private ToDoListAdapter toDoListAdapter;
    private RecyclerView recyclerViewV;
    private ArrayList<ToDo> toDoArrayList;
    private FloatingActionButton floatingActionButton;
    private EditText bodyEditText;
    private EditText titleEditText;

    public static ToDoFragment getInstance(Context context, ArrayList<ToDo> toDos) {
        Bundle bundle = new Bundle();
        ToDoFragment fragment = new ToDoFragment();
        fragment.setArguments(bundle);
        fragment.setData(toDos);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_todo));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        initAdapters();
        initFab();
        return view;
    }

    private void initFab() {
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_toDo);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(false, null);
            }
        });
    }

    private void showAlertDialog(final boolean edit, final ToDo toDo) {
        LayoutInflater inflater = LayoutInflater.from(context);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        View viewDialog = inflater.inflate(R.layout.alertdialog_todo, null);

        titleEditText = (EditText) viewDialog.findViewById(R.id.title_todo_editText);
        bodyEditText = (EditText) viewDialog.findViewById(R.id.node_todo_editText);

        if (edit == false) {
            builder.title(getString(R.string.create_new_toDo));
        } else {
            builder.title(getString(R.string.refactor));
            titleEditText.setText(toDo.getType());
            bodyEditText.setText(toDo.getBody());
        }

        builder.customView(viewDialog, true);

        builder.positiveText(getString(R.string.save));
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                ToDo newToDo = null;
                if (edit == true) {
                    if  (titleEditText.getText().equals("")) {
                        newToDo.setType(toDo.getType());
                    } else if (bodyEditText.getText().equals("")) {
                        newToDo.setBody(toDo.getBody());
                    } else {
                        newToDo = new ToDo(titleEditText.getText().toString(), bodyEditText.getText().toString());
                    }
                    SharedPreferencesHelper.getInstance().saveNewAction(new UserAction(getString(R.string.action) ,getString(R.string.refactored)));
                } else {
                    newToDo = new ToDo(titleEditText.getText().toString(), bodyEditText.getText().toString());
                    SharedPreferencesHelper.getInstance().saveNewAction(new UserAction(getString(R.string.action) ,getString(R.string.created)));
                }

                toDoArrayList.add(newToDo);
                SharedPreferencesHelper.getInstance().saveNewToDo(toDoArrayList);
                toDoListAdapter.setDataToDoListAdapter(toDoArrayList);

                dialog.dismiss();
            }
        });

        builder.negativeText(getString(R.string.exit));
        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void initAdapters() {
        toDoListAdapter = new ToDoListAdapter(toDoArrayList, this, context);
        recyclerViewV = (RecyclerView) view.findViewById(R.id.todo_recyclerView);
        recyclerViewV.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewV.setAdapter(toDoListAdapter);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<ToDo> toDos) {
        toDoArrayList = toDos;
    }

    @Override
    public void editToDoCard(int position) {
        toDoArrayList = SharedPreferencesHelper.getInstance().getToDos();
        showAlertDialog(true, toDoArrayList.get(position));
        toDoArrayList.remove(position);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferencesHelper.getInstance().saveNewToDo(toDoArrayList);
    }
}
