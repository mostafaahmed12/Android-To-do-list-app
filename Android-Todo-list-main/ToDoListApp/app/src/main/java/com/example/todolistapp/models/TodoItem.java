package com.example.todolistapp.models;

public class TodoItem {
    int id;
    String title;
    String desc;
    int user_id;
    int is_done;

    public TodoItem() {

    }

    public TodoItem(int id, String title , String desc) {
        this.id = id;
        this.title = title;
        this.desc=desc;
    }

    public TodoItem(int id, String title, String desc, int user_id, int is_done) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.user_id = user_id;
        this.is_done = is_done;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getIs_done() {
        return is_done;
    }

    public void setIs_done(int is_done) {
        this.is_done = is_done;
    }

}
