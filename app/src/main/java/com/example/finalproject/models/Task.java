package com.example.finalproject.models;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.util.Date;

public class Task {
    private long id;
    private String description = "";
    private Date due;
    private boolean done;

    public Task(long id, String description, Date due, boolean done) {
        this.id = id;
        this.description = description;
        this.due = due;
        this.done = done;
    }
    public Task(String description, Date due, boolean done) {
        this.description = description;
        this.due = due;
        this.done = done;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public boolean isDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }
    public Date getDue() {
        return due;
    }
    public void setDue(Date due) {
        this.due = due;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {

        return String.format("ID: %d DESC: %s DUE: %s DONE: %b", this.id, this.description, this.due, this.done);
    }

    public boolean isValid(){
        if(description.isEmpty()) {
            return false;
        } else return due != null;
    }

}
