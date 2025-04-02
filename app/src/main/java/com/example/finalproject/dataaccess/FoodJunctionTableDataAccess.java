package com.example.finalproject.dataaccess;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalproject.models.Food;
import com.example.finalproject.models.FoodList;
import com.example.finalproject.models.MySQLiteHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FoodJunctionTableDataAccess {

    public static final String TABLE_NAME = "FoodList_Food";
    public static final String TABLE_FOOD_LIST = "FoodList";
    public static final String COLUMN_FOOD_LIST_ID = "foodListId";
    public static final String TABLE_FOOD = "Food";
    public static final String COLUMN_FOOD_ID = "foodId";
    SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");

    public static final String TABLE_CREATE = String.format("CREATE TABLE %s ( %s INTEGER NOT NULL, %s INTEGER NOT NULL, PRIMARY KEY (%s,%s), FOREIGN KEY (%s) REFERENCES %s(id) ON DELETE CASCADE, FOREIGN KEY (%s) REFERENCES %s(id) ON DELETE CASCADE)",
            TABLE_NAME,
            COLUMN_FOOD_LIST_ID,
            COLUMN_FOOD_ID,
            COLUMN_FOOD_LIST_ID,
            COLUMN_FOOD_ID,
            COLUMN_FOOD_LIST_ID,
            TABLE_FOOD_LIST,
            COLUMN_FOOD_ID,
            TABLE_FOOD
            );

    private Context context;
    private MySQLiteHelper dbHelper;
    private SQLiteDatabase database; // the database object which we run our queries

    public FoodJunctionTableDataAccess(Context context){
        this.context = context;
        this.dbHelper = new MySQLiteHelper(context);
        this.database = this.dbHelper.getWritableDatabase();
    }

    public void addFoodToList(int foodId, int foodListId) {
        String query = String.format("INSERT INTO FoodList_Food (%s, %s) VALUES (" + foodId + "," + foodListId + ");", COLUMN_FOOD_ID, COLUMN_FOOD_LIST_ID);
        Cursor c = database.rawQuery(query, null);
    }

    public int deleteFoodFromList(FoodList t) {
        int rowsDeleted = database.delete(TABLE_NAME, COLUMN_FOOD_LIST_ID + "=" + t.getId(), null);
        return rowsDeleted;
    }

    public ArrayList<Food> getAllFoodFromFoodList(int foodListId){
        ArrayList<Food> foods = new ArrayList<>();
        String query = String.format("SELECT %s.* FROM %s JOIN %s ON %s.id = %s.%s WHERE %s.%s =" + foodListId + ";",
                TABLE_FOOD,
                TABLE_FOOD,
                TABLE_NAME,
                TABLE_FOOD,
                TABLE_NAME,
                COLUMN_FOOD_ID,
                TABLE_NAME,
                COLUMN_FOOD_LIST_ID
                );
        Cursor c = database.rawQuery(query, null);
        if(c != null && c.getCount() > 0){
            c.moveToFirst(); // have to do this so you can properly loop through the cursor object
            while(!c.isAfterLast()){
                long id = c.getLong(0);
                int sweet = c.getInt(1);
                int salty = c.getInt(2);
                int sour = c.getInt(3);
                int bitter = c.getInt(4);
                int umami = c.getInt(5);
                String countryOfOrigin = c.getString(6);
                boolean spicy = c.getLong(7) == 1;
                String name = c.getString(8);
                String description = c.getString(9);
                Food f = new Food(id, sweet, salty, sour, bitter, umami, countryOfOrigin, spicy, name, description);
                foods.add(f);
                c.moveToNext(); //kind of like how you add 1 to i in a for loop
            }
            c.close();
        }
        return foods;
    }

    public ArrayList<FoodList> getAllFoodListFromFood(int foodId){
        ArrayList<FoodList> foodLists = new ArrayList<>();
        String query = String.format("SELECT %s.* FROM %s JOIN %s ON %s.id = %s.%s WHERE %s.%s =" + foodId + ";",
                TABLE_FOOD_LIST,
                TABLE_FOOD_LIST,
                TABLE_NAME,
                TABLE_FOOD_LIST,
                TABLE_NAME,
                COLUMN_FOOD_LIST_ID,
                TABLE_NAME,
                COLUMN_FOOD_ID
        );
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

//--selects all foods inside the foodlist with the id of 2
//SELECT Food.*
//FROM Food
//JOIN FoodList_Food ON Food.id = FoodList_Food.foodId
//WHERE FoodList_Food.foodListId = 2;

//    public ArrayList<FoodList> getAllFoodLists( int id) {// either int id or a food model and get the id from that model
//        ArrayList<FoodList> foodLists = new ArrayList<>();
//        String query = String.format("SELECT %s, %s, %s, %s FROM %s", COLUMN_FOOD_LIST_ID, COLUMN_LIST_NAME, COLUMN_FIRST_CREATED, COLUMN_LAST_UPDATED, TABLE_NAME);
//        Cursor c = database.rawQuery(query, null);
//        if (c != null && c.getCount() > 0) {
//            c.moveToFirst(); // have to do this so you can properly loop through the cursor object
//            while (!c.isAfterLast()) {
//                long id = c.getLong(0);
//                String listName = c.getString(1);
//                String firstCreated = c.getString(2);
//                String lastUpdated = c.getString(3);
//                Date firstCreatedDate = null;
//                Date lastUpdatedDate = null;
//                try {
//                    firstCreatedDate = dateFormat.parse(firstCreated);
//                    lastUpdatedDate = dateFormat.parse(lastUpdated);
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }
//                FoodList t = new FoodList(id, listName, firstCreatedDate, lastUpdatedDate);
//                foodLists.add(t);
//                c.moveToNext(); //kind of like how you add 1 to i in a for loop
//            }
//            c.close();
//        }
//        return foodLists;
//    }
}
