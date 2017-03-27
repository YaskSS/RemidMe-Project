package com.example.yass.remindme.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.yass.remindme.MainActivity;
import com.example.yass.remindme.models.Remind;
import com.example.yass.remindme.models.ToDo;
import com.example.yass.remindme.models.UserAction;
import com.example.yass.remindme.models.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yass on 2/17/17.
 */

public class SharedPreferencesHelper {

    public static final String APP_PREFERENCES = "dataBase";

    public static final String HISTORY = "history";
    private static final String REMIND = "remind";
    private static final String WORDS = "words";
    private static final String TODO = "todo";

    private static SharedPreferencesHelper ourInstance;
    private ArrayList<Remind> listRemind;

    public static SharedPreferencesHelper getInstance() {
        Context context = MainActivity.getContext();
        if (ourInstance == null) {
            ourInstance = new SharedPreferencesHelper(context);
        }

        return ourInstance;
    }

    private SharedPreferences sharedPreferences;

    private SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public boolean saveNewAction(UserAction userAction) {
        ArrayList<UserAction> actionsSaved = getListUserActions();
        ArrayList<UserAction> userActions;


        if (null == userAction) {
            return false;
        } else {
            userActions = new ArrayList<>();
            userActions.add(userAction);
            userActions.addAll(actionsSaved);
            actionsSaved.add(userAction);
            String jsonString = UserAction.toJSONCollection(userActions);
            sharedPreferences.edit().putString(HISTORY, jsonString).apply();
            return true;
        }
    }

    public void deleteAllActions() {
        ArrayList<UserAction> actionsSaved = new ArrayList<>();
        String jsonString = UserAction.toJSONCollection(actionsSaved);
        sharedPreferences.edit().putString(HISTORY, jsonString).apply();
    }

    public ArrayList<UserAction> getListUserActions() {
        ArrayList<UserAction> actionsSaved;
        ArrayList<UserAction> userActions ;

        if (sharedPreferences.getString(HISTORY, null) != null) {
            actionsSaved = new ArrayList<>(UserAction.fromJsonCollection(sharedPreferences.getString(HISTORY, null)));
            /*userActions = actionsSaved;

            Log.i("userActions.size()= ","" + userActions.size());

            if (actionsSaved.size() != 0) {

                int indexSize = actionsSaved.size()-1;

                Log.i("actionsSaved.size()=","" + actionsSaved.size());

                for (int i = 0; i < actionsSaved.size(); i++) {
                    Log.i("indexSizeStart = ", ""+ indexSize);
                    indexSize = indexSize - i;
                    Log.i("indexSizeMiddle = ", ""+ indexSize);
                    userActions.add(indexSize, actionsSaved.get(i));
                    Log.i("indexSizeFinish = ", ""+ indexSize);

                }
            }*/
            //Log.i("userActions.size()= ","" + userActions.size());
            //return Collections.reverse(actionsSaved);
        } else {
            actionsSaved = new ArrayList<>();
        }

        return actionsSaved;
    }

    public boolean saveNewRemind(Remind remind) {
        ArrayList<Remind> reminds = SharedPreferencesHelper.getInstance().getListRemind();

        if (remind == null) {
            return false;
        } else {
            reminds.add(remind);
            String jsonString = Remind.toJson(reminds);
            sharedPreferences.edit().putString(REMIND, jsonString).apply();
            return true;
        }
    }

    public boolean saveNewRemind(ArrayList<Remind> reminds) {
        String jsonString = Remind.toJson(reminds);
        sharedPreferences.edit().putString(REMIND, jsonString).apply();
        return true;
    }

    public ArrayList<Remind> getListRemind() {
        ArrayList<Remind> arrayList;

        if (sharedPreferences.getString(REMIND, null) != null) {
            arrayList = new ArrayList<>(Remind.fromJson(sharedPreferences.getString(REMIND, null)));
        } else {
            arrayList = new ArrayList<>();
        }
        return arrayList;
    }

    public boolean saveNewWords(ArrayList<Word> words) {
        String jsonString = Word.toJSON(words);
        sharedPreferences.edit().putString(WORDS, jsonString).apply();
        return true;
    }

    public boolean saveNewWord(Word word) {
        ArrayList<Word> words = getWords();

        if (word == null) {
            return false;
        } else {
            words.add(word);
            String jsonString = Word.toJSON(words);
            sharedPreferences.edit().putString(WORDS, jsonString).apply();
            return true;
        }
    }


    public ArrayList<Word> getWords() {
        ArrayList<Word> words;

        if (sharedPreferences.getString(WORDS, null) != null) {
            words = new ArrayList<>(Word.fromJSON(sharedPreferences.getString(WORDS, null)));
        } else {
            words = new ArrayList<>();
        }
        return words;
    }

    public boolean saveNewToDo(ToDo toDo){
        ArrayList<ToDo> toDos = getToDos();

        if (toDo == null){
            return  false;
        } else {
            toDos.add(toDo);
            String jsonToDo = ToDo.toJSON(toDos);
            sharedPreferences.edit().putString(TODO, jsonToDo).apply();
            return true;
        }
    }

    public boolean saveNewToDo(ArrayList<ToDo> toDos){

        if (toDos == null){
            return  false;
        } else {

            String jsonToDo = ToDo.toJSON(toDos);
            sharedPreferences.edit().putString(TODO, jsonToDo).apply();
            return true;
        }
    }

    public ArrayList<ToDo> getToDos(){
        ArrayList<ToDo> toDoArrayList;

        if (sharedPreferences.getString(TODO, null) == null){
             toDoArrayList = new ArrayList<ToDo>();
        } else {
             toDoArrayList = new ArrayList<>(ToDo.fromJSON(sharedPreferences.getString(TODO, null)));
        }
        return toDoArrayList;
    }
}
