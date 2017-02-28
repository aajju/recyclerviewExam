package com.aajju.recyclerviewexam;

import java.io.Serializable;

/**
 * Created by aajju on 2017-02-24.
 */

public class Memo implements Serializable{
    private int id;
    private String title, contents;
    private long time;

    public Memo(String title, String contents, long time) {
        this.title = title;
        this.contents = contents;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
//
//    { "id" : 1,
//            "title" = "abc"
//        "contents" : "ccc"
//        "time" : 123456789
//    }

    @Override
    public String toString() {
        return "{\"id\":" + id + ", \"title\":\"" + title + "\", \"contents\":\"" + contents + "\", \"time\":" + time + "}";
    }
}
