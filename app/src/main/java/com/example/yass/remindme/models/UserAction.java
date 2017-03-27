package com.example.yass.remindme.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yass on 1/31/17.
 */

public class UserAction {

    private String action;
    private String date;
    private String body;

    private static transient Gson gson = new Gson();

    public UserAction(String action, String body){
        this.action =  action;
        date = new SimpleDateFormat("yyyy.MM.dd''HH:mm").format(Calendar.getInstance().getTime());
        this.body = body;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static String toJSONCollection(List<UserAction> userAction){
        return gson.toJson(userAction);
    }

    public static List<UserAction> fromJsonCollection(String userAction){
        return gson.fromJson(userAction, new TypeToken<ArrayList<UserAction>>() {
        }.getType());
    }
}
