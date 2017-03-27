package com.example.yass.remindme.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by yass on 2/22/17.
 */

public class Word {

    private String title;

    private static transient Gson gson = new Gson();

    public Word(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static String toJSON(ArrayList<Word> words){return gson.toJson(words);}

    public static ArrayList<Word> fromJSON(String words){
        return gson.fromJson(words,new TypeToken<ArrayList<Word>>(){}.getType());
    }
}
