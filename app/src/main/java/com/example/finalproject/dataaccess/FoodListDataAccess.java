package com.example.finalproject.dataaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalproject.models.Food;
import com.example.finalproject.models.FoodList;
import com.example.finalproject.models.MySQLiteHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FoodListDataAccess {

    public static final String TABLE_NAME = "FoodList";
    public static final String COLUMN_FOOD_LIST_ID = "id";
    public static final String COLUMN_LIST_NAME = "listName";
    public static final String COLUMN_FIRST_CREATED = "firstCreated";
    public static final String COLUMN_LAST_UPDATED = "lastUpdated";
    SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");

    public static final String TABLE_CREATE = String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL DEFAULT '', %s TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, %s TEXT DEFAULT NULL)",
            TABLE_NAME,
            COLUMN_FOOD_LIST_ID,
            COLUMN_LIST_NAME,
            COLUMN_FIRST_CREATED,
            COLUMN_LAST_UPDATED);

    private Context context;
    private MySQLiteHelper dbHelper;
    private SQLiteDatabase database; // the database object which we run our queries
    private FoodJunctionTableDataAccess FDataAccess;

    public FoodListDataAccess(Context context){
        this.context = context;
        this.dbHelper = new MySQLiteHelper(context);
        this.database = this.dbHelper.getWritableDatabase();
        this.FDataAccess = new FoodJunctionTableDataAccess(context);
    }
//    @Override
public ArrayList<FoodList> getAllFoodLists() {
    ArrayList<FoodList> foodLists = new ArrayList<>();
    String query = String.format("SELECT %s, %s, %s, %s FROM %s", COLUMN_FOOD_LIST_ID, COLUMN_LIST_NAME, COLUMN_FIRST_CREATED, COLUMN_LAST_UPDATED, TABLE_NAME);
    Cursor c = database.rawQuery(query, null);
    if (c != null && c.getCount() > 0) {
        c.moveToFirst(); // have to do this so you can properly loop through the cursor object
        while (!c.isAfterLast()) {
            long id = c.getLong(0);
            String listName = c.getString(1);
            String firstCreated = c.getString(2);
            String lastUpdated = c.getString(3);
            Date firstCreatedDate = null;
            Date lastUpdatedDate = null;
            try {
                firstCreatedDate = dateFormat.parse(firstCreated);
                lastUpdatedDate = dateFormat.parse(lastUpdated);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            FoodList t = new FoodList(id, listName, firstCreatedDate, lastUpdatedDate);
            foodLists.add(t);
            c.moveToNext(); //kind of like how you add 1 to i in a for loop
        }
        c.close();
    }
    return foodLists;
}

    //    @Override
    public FoodList getFoodListById(long id) {
        String query = String.format("SELECT %s, %s, %s, %s FROM %s WHERE %s = %d", COLUMN_FOOD_LIST_ID, COLUMN_LIST_NAME, COLUMN_FIRST_CREATED, COLUMN_LAST_UPDATED, TABLE_NAME, COLUMN_FOOD_LIST_ID, id);
        Cursor c = database.rawQuery(query, null);
        c.moveToFirst();
        String listName = c.getString(1);
        String firstCreated = c.getString(2);
        String lastUpdated = c.getString(3);
        Date firstCreatedDate = null;
        Date lastUpdatedDate = null;
        try {
            firstCreatedDate = dateFormat.parse(firstCreated);
            lastUpdatedDate = dateFormat.parse(lastUpdated);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        c.close();
        return new FoodList(id, listName, firstCreatedDate, lastUpdatedDate);

    }

    //    @Override
    public FoodList insertFoodList(FoodList t) {
        ContentValues values = new ContentValues(); //like a hashmap
        values.put(COLUMN_LIST_NAME, t.getListName()); //put is the way to add a key value pair into a hashmap
        values.put(COLUMN_FIRST_CREATED, dateFormat.format(t.getFirstCreated()));
        values.put(COLUMN_LAST_UPDATED, dateFormat.format(t.getLastUpdated()));

        long insertId = database.insert(TABLE_NAME, null, values); //returns the inserted values' table id
        //note: if the insert fails, it will return -1
        t.setId(insertId);
        return t;
    }

    //    @Override
    public FoodList updateFoodList(FoodList t) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_LIST_NAME, t.getListName());
        values.put(COLUMN_LAST_UPDATED, dateFormat.format(t.getLastUpdated()));
        int rowsUpdated = database.update(TABLE_NAME, values, COLUMN_FOOD_LIST_ID + "=" + t.getId(), null);

        return t;
    }

    //    @Override
    public int deleteFoodList(FoodList t) {
        int rowsDeleted = database.delete(TABLE_NAME, COLUMN_FOOD_LIST_ID + "=" + t.getId(), null);
        return rowsDeleted;
    }

    public void addFoodToList(int foodId, int foodListId){
        FDataAccess.addFoodToList(foodId, foodListId);
    }

    public ArrayList<Food> getAllFoodFromFoodList(int foodListId){
        return FDataAccess.getAllFoodFromFoodList(foodListId);
    }

}
