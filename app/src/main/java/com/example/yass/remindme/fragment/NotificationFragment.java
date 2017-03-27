package com.example.yass.remindme.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.yass.remindme.R;
import com.example.yass.remindme.adapter.RemindListAdapter;
import com.example.yass.remindme.db.SharedPreferencesHelper;
import com.example.yass.remindme.interfaces.RemindListAdapterCallBack;
import com.example.yass.remindme.models.Remind;
import com.example.yass.remindme.models.UserAction;
import com.example.yass.remindme.models.Word;

import java.util.ArrayList;

/**
 * Created by yass on 1/29/17.
 */

public class NotificationFragment extends AbstractTabFragment implements RemindListAdapterCallBack {

    //http://developer.alexanderklimov.ru/android/theory/resources.php

    private static final int LAYOUT = R.layout.fragment_remind;

    private FloatingActionButton fab;
    private RemindListAdapter remindListAdapter;
    private ArrayList<Remind> reminds;
    private RecyclerView recyclerView;
    private Spinner spinner;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    String[] dataString = new String[4];
    private EditText noteEditText;
    private String chosenElement;
    private InputMethodManager inputMethodManager;

    public static NotificationFragment getInstance(Context context, ArrayList<Remind> reminds) {
        Bundle bundle = new Bundle();
        NotificationFragment fragment = new NotificationFragment();
        fragment.setArguments(bundle);
        fragment.setData(reminds);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_remind));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        dataString[0] = getResources().getString(R.string.read_remind);
        dataString[1] = getResources().getString(R.string.write_remind);
        dataString[2] = getResources().getString(R.string.read);
        dataString[3] = getResources().getString(R.string.repeat);

        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        initAdapters();
        initFab();
        return view;
    }

    private void initAdapters() {
        remindListAdapter = new RemindListAdapter(reminds, this, context);
        recyclerView = (RecyclerView) view.findViewById(R.id.remind_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(remindListAdapter);
    }

    private void initFab() {
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //createDialog();
                showDialog(false, null);
            }
        });
    }

    private void createDialog() {
       /* LayoutInflater inflater = LayoutInflater.from(context);
        View viewDialog = inflater.inflate(R.layout.alertdialog_remind, null);
        dialogBuilder = new AlertDialog.Builder(context);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,dataString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner) viewDialog.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setPrompt(getResources().getString(R.string.enter_choice));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosenElement = dataString[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        noteEditText = (EditText) viewDialog.findViewById(R.id.note_editText);
        Button saveButton = (Button) viewDialog.findViewById(R.id.save_button_alertDialog_remind);
        saveButton.setOnClickListener(saveButtonOnClickListener);
        final Button exitButton = (Button) viewDialog.findViewById(R.id.exit_button_alertDialog_remind);

        exitButton.setOnClickListener(exitButtonOnclickListener);

        dialogBuilder.setTitle(R.string.create_new_notification);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setView(viewDialog);
        dialog = dialogBuilder.create();
        showKeyboard();
        dialog.show();*/
    }

    View.OnClickListener saveButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SharedPreferencesHelper.getInstance().saveNewRemind(new Remind(chosenElement, noteEditText.getText().toString()));
            remindListAdapter.setData(SharedPreferencesHelper.getInstance().getListRemind());
            dialog.dismiss();
        }
    };

    View.OnClickListener exitButtonOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    };

    public void setData(ArrayList<Remind> reminds) {
        this.reminds = reminds;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private void showKeyboard() {
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    public void editRemindCard(int position) {
        reminds = SharedPreferencesHelper.getInstance().getListRemind();
        showDialog(true, reminds.get(position));
        reminds.remove(position);
    }

    private void showDialog(final boolean edit, final Remind remind) {
        LayoutInflater inflater = LayoutInflater.from(context);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        View viewDialog = inflater.inflate(R.layout.alertdialog_remind, null);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, dataString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noteEditText = (EditText) viewDialog.findViewById(R.id.note_editText);

        if (edit == false) {
            builder.title(getString(R.string.create_new_notification));
        } else {
            builder.title(remind.getType());
            noteEditText.setText(remind.getNote());
        }

        spinner = (Spinner) viewDialog.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setPrompt(getResources().getString(R.string.enter_choice));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosenElement = dataString[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        builder.customView(viewDialog, true);

        builder.positiveText(getString(R.string.save));
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Remind remindNew = null;
                if (edit == true) {
                    if (noteEditText.getText().equals("")) {
                        remindNew = remind;
                    } else {
                        remindNew = new Remind(chosenElement, noteEditText.getText().toString());
                    }
                    SharedPreferencesHelper.getInstance().saveNewAction(new UserAction(getString(R.string.remind),getString(R.string.refactored)));
                } else {
                    remindNew = new Remind(chosenElement, noteEditText.getText().toString());
                    SharedPreferencesHelper.getInstance().saveNewAction(new UserAction(getString(R.string.remind), getString(R.string.created)));
                }
                reminds.add(remindNew);
                SharedPreferencesHelper.getInstance().saveNewRemind(reminds);
                remindListAdapter.setData(SharedPreferencesHelper.getInstance().getListRemind());

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
}
