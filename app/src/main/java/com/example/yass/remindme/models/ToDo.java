package com.example.yass.remindme.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by yass on 3/23/17.
 */

public class ToDo {

    private String type;
    private String body;

    public ToDo(String type, String body) {
        this.type = type;
        this.body = body;
    }

    private static transient Gson gson = new Gson();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static  String toJSON(ArrayList<ToDo> toDos){ return gson.toJson(toDos);}

    public static ArrayList<ToDo> fromJSON(String toDos){ return gson.fromJson(toDos, new TypeToken<ArrayList<ToDo>>(){}.getType());}
}
