package com.example.finalproject.dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalproject.models.MySQLiteHelper;

public class FoodListDataAccess {

//-- Create the Food table
//    CREATE TABLE Food (
//            id INTEGER PRIMARY KEY AUTOINCREMENT,
//            sweet INTEGER NOT NULL,
//            salty INTEGER NOT NULL,
//            sour INTEGER NOT NULL,
//            bitter INTEGER NOT NULL,
//            umami INTEGER NOT NULL,
//            countryOfOrigin TEXT,
//            spicy BOOLEAN NOT NULL DEFAULT 0,
//            name TEXT NOT NULL,
//            description TEXT
//    );
//
//-- Create the FoodList table
//    CREATE TABLE FoodList (
//            id INTEGER PRIMARY KEY AUTOINCREMENT,
//            listName TEXT NOT NULL DEFAULT '',
//            firstCreated TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
//            lastUpdated TEXT DEFAULT NULL
//    );
//
//-- Create a junction table to represent the many-to-many relationship between Food and FoodList
//    CREATE TABLE FoodList_Food (
//            foodListId INTEGER NOT NULL,
//            foodId INTEGER NOT NULL,
//            PRIMARY KEY (foodListId, foodId),
//    FOREIGN KEY (foodListId) REFERENCES FoodList(id) ON DELETE CASCADE,
//    FOREIGN KEY (foodId) REFERENCES Food(id) ON DELETE CASCADE
//);
//
//-- Create indexes for better query performance
//    CREATE INDEX idx_Food_countryOfOrigin ON Food(countryOfOrigin);
//    CREATE INDEX idx_FoodList_listName ON FoodList(listName);
//    CREATE INDEX idx_FoodList_Food_foodListId ON FoodList_Food(foodListId);
//    CREATE INDEX idx_FoodList_Food_foodId ON FoodList_Food(foodId);

    public static final String TABLE_NAME = "FoodList";
    public static final String COLUMN_FOOD_LIST_ID = "id";
    public static final String COLUMN_LIST_NAME = "listName";
    public static final String COLUMN_FIRST_CREATED = "firstCreated";
    public static final String COLUMN_LAST_UPDATED = "lastUpdated";
    public static final String TABLE_CREATE = String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL DEFAULT '', %s TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, %s TEXT DEFAULT NULL)",
            TABLE_NAME,
            COLUMN_FOOD_LIST_ID,
            COLUMN_LIST_NAME,
            COLUMN_FIRST_CREATED,
            COLUMN_LAST_UPDATED);

    private Context context;
    private MySQLiteHelper dbHelper;
    private SQLiteDatabase database; // the database object which we run our queries

    public FoodListDataAccess(Context context){
        this.context = context;
        this.dbHelper = new MySQLiteHelper(context);
        this.database = this.dbHelper.getWritableDatabase();
    }

}
