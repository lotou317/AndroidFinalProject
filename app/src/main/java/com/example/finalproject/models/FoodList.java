package com.example.finalproject.models;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FoodList {
    private long id;
    private String listName = "";
    private Date firstCreated = new Date();
    private Date lastUpdated;
    private List<Food> foodList = new ArrayList<>();

    public FoodList(long id, String listName) {
        this.id = id;
        this.listName = listName;
        this.lastUpdated = new Date();
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {

        return this.listName;
    }


}
