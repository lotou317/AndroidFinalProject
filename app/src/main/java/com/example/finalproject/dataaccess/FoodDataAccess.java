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
    private FoodJunctionTableDataAccess FDataAccess;

    public FoodDataAccess(Context context){
        this.context = context;
        this.dbHelper = new MySQLiteHelper(context);
        this.database = this.dbHelper.getWritableDatabase();
        this.FDataAccess = new FoodJunctionTableDataAccess(context);
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

    //Junction Table methods

    public void addFoodToList(int foodId, int foodListId){
        FDataAccess.addFoodToList(foodId, foodListId);
    }

    public ArrayList<FoodList> getAllFoodListFromFood(int foodId) {
        return FDataAccess.getAllFoodListFromFood(foodId);
    }

    public int deleteFoodFromList(int foodId, int foodListId) {
        return FDataAccess.deleteFoodFromList(foodId, foodListId);
    }

}


//// db queries

//--selects all foods inside the foodlist with the id of 2
//SELECT Food.*
//FROM Food
//JOIN FoodList_Food ON Food.id = FoodList_Food.foodId
//WHERE FoodList_Food.foodListId = 2;


//INSERT INTO FoodList_Food (foodListId, foodId) VALUES
//(2, 1)

//
//-- Insert sample foods
//INSERT INTO Food (sweet, salty, sour, bitter, umami, countryOfOrigin, spicy, name, description) VALUES
//(5, 2, 3, 1, 4, 'Japan', 1, 'Sushi', 'A traditional Japanese dish with vinegared rice and raw fish.'),
//        (2, 4, 1, 3, 5, 'Italy', 0, 'Pizza', 'A popular Italian dish with a baked crust, cheese, and toppings.'),
//        (3, 5, 2, 1, 4, 'Mexico', 1, 'Tacos', 'A Mexican dish consisting of a folded tortilla filled with meat and toppings.'),
//        (4, 2, 5, 1, 3, 'India', 1, 'Curry', 'A spicy Indian dish with a thick sauce and various spices.'),
//        (1, 3, 4, 5, 2, 'China', 0, 'Dumplings', 'A Chinese dish consisting of dough filled with meat or vegetables.'),
//        (3, 2, 1, 4, 5, 'France', 0, 'Croissant', 'A buttery, flaky French pastry.'),
//        (4, 3, 5, 2, 1, 'Thailand', 1, 'Pad Thai', 'A Thai noodle dish with a sweet-savory sauce and peanuts.');
//
//        -- Insert sample food lists
//INSERT INTO FoodList (listName, firstCreated, lastUpdated) VALUES
//('Favorite Foods', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
//        ('Spicy Lovers', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
//        ('Savory Dishes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
//
//        -- Associate foods with food lists
//INSERT INTO FoodList_Food (foodListId, foodId) VALUES
//(1, 1), -- Sushi in Favorite Foods
//        (1, 2), -- Pizza in Favorite Foods
//        (1, 3), -- Tacos in Favorite Foods
//        (2, 4), -- Curry in Spicy Lovers
//        (2, 3), -- Tacos in Spicy Lovers
//        (2, 7), -- Pad Thai in Spicy Lovers
//        (3, 2), -- Pizza in Savory Dishes
//        (3, 4), -- Curry in Savory Dishes
//        (3, 5); -- Dumplings in Savory Dishes
