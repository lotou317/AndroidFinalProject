package com.example.finalproject.dataaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalproject.models.Food;
import com.example.finalproject.models.FoodList;
import com.example.finalproject.models.MySQLiteHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class FoodDataAccess {
    public static final String TAG = "FoodDataAccess";
    public static final String TABLE_NAME = "Food";
    public static final String COLUMN_FOOD_ID = "id";
    public static final String COLUMN_SWEET = "sweet";
    public static final String COLUMN_SALTY = "salty";
    public static final String COLUMN_SOUR = "sour";
    public static final String COLUMN_BITTER = "bitter";
    public static final String COLUMN_UMAMI = "umami";
    public static final String COLUMN_COUNTRY_OF_ORIGIN = "countryOfOrigin";
    public static final String COLUMN_SPICY = "spicy";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String TABLE_CREATE = String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT NULL, %s TEXT, %s BOOLEAN NOT NULL DEFAULT 0, %s TEXT NOT NULL, %s TEXT)",
            TABLE_NAME,
            COLUMN_FOOD_ID,
            COLUMN_SWEET,
            COLUMN_SALTY,
            COLUMN_SOUR,
            COLUMN_BITTER,
            COLUMN_UMAMI,
            COLUMN_COUNTRY_OF_ORIGIN,
            COLUMN_SPICY,
            COLUMN_NAME,
            COLUMN_DESCRIPTION
    );

    private Context context;
    private MySQLiteHelper dbHelper;
    private SQLiteDatabase database; // the database object which we run our queries

    public FoodDataAccess(Context context){
        this.context = context;
        this.dbHelper = new MySQLiteHelper(context);
        this.database = this.dbHelper.getWritableDatabase();
    }

    public ArrayList<Food> getAllFoods() {
        ArrayList<Food> foods = new ArrayList<>();
        String query = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s",
            COLUMN_FOOD_ID,
            COLUMN_SWEET,
            COLUMN_SALTY,
            COLUMN_SOUR,
            COLUMN_BITTER,
            COLUMN_UMAMI,
            COLUMN_COUNTRY_OF_ORIGIN,
            COLUMN_SPICY,
            COLUMN_NAME,
            COLUMN_DESCRIPTION,
            TABLE_NAME
        );
        Cursor c = database.rawQuery(query,null);
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

    //    @Override
    public Food getFoodById(long id) {
        String query = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s",
                COLUMN_FOOD_ID,
                COLUMN_SWEET,
                COLUMN_SALTY,
                COLUMN_SOUR,
                COLUMN_BITTER,
                COLUMN_UMAMI,
                COLUMN_COUNTRY_OF_ORIGIN,
                COLUMN_SPICY,
                COLUMN_NAME,
                COLUMN_DESCRIPTION,
                TABLE_NAME
        );
        Cursor c = database.rawQuery(query, null);
        c.moveToFirst();
        int sweet = c.getInt(1);
        int salty = c.getInt(2);
        int sour = c.getInt(3);
        int bitter = c.getInt(4);
        int umami = c.getInt(5);
        String countryOfOrigin = c.getString(6);
        boolean spicy = c.getLong(7) == 1;
        String name = c.getString(8);
        String description = c.getString(9);
        c.close();
        return new Food(id, sweet, salty, sour, bitter, umami, countryOfOrigin, spicy, name, description);

    }

    //    @Override
    public Food insertFood(Food f) {
        ContentValues values = new ContentValues(); //like a hashmap
        values.put(COLUMN_NAME, f.getName()); //put is the way to add a key value pair into a hashmap
        values.put(COLUMN_SWEET, f.getSweet());
        values.put(COLUMN_SALTY, f.getSalty());
        values.put(COLUMN_SOUR, f.getSour());
        values.put(COLUMN_BITTER, f.getBitter());
        values.put(COLUMN_UMAMI, f.getUmami());
        values.put(COLUMN_COUNTRY_OF_ORIGIN, f.getCountryOfOrigin());
        values.put(COLUMN_SPICY, f.isSpicy());
        values.put(COLUMN_DESCRIPTION, f.getDescription());

        long insertId = database.insert(TABLE_NAME, null, values); //returns the inserted values' table id
        //note: if the insert fails, it will return -1
        f.setId(insertId);
        return f;
    }

    //    @Override
    public Food updateFood(Food f) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, f.getName()); //put is the way to add a key value pair into a hashmap
        values.put(COLUMN_SWEET, f.getSweet());
        values.put(COLUMN_SALTY, f.getSalty());
        values.put(COLUMN_SOUR, f.getSour());
        values.put(COLUMN_BITTER, f.getBitter());
        values.put(COLUMN_UMAMI, f.getUmami());
        values.put(COLUMN_COUNTRY_OF_ORIGIN, f.getCountryOfOrigin());
        values.put(COLUMN_SPICY, f.isSpicy());
        values.put(COLUMN_DESCRIPTION, f.getDescription());
        int rowsUpdated = database.update(TABLE_NAME, values, COLUMN_FOOD_ID + "=" + f.getId(), null);

        return f;
    }

    //    @Override
    public int deleteFood(Food t) {
        int rowsDeleted = database.delete(TABLE_NAME, COLUMN_FOOD_ID + "=" + t.getId(), null);
        return rowsDeleted;
    }
}
