package com.example.yass.remindme.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yass on 2/20/17.
 */

public class Remind {

    private String type;
    private String note;

    private static transient Gson gson = new Gson();

    public Remind(String type, String note) {
        this.type = type;
        this.note = note;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public static String toJson(List<Remind> reminds){
        return gson.toJson(reminds);
    }

    public static ArrayList<Remind> fromJson(String reminds){
        return gson.fromJson(reminds, new TypeToken<ArrayList<Remind>>(){}.getType());
    }
}
