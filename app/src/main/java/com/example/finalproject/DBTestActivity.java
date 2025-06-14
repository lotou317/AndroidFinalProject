package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.dataaccess.FoodDataAccess;
import com.example.finalproject.dataaccess.FoodListDataAccess;
import com.example.finalproject.models.Food;
import com.example.finalproject.models.FoodList;

import java.util.Date;

public class DBTestActivity extends AppCompatActivity {
    public static final String TAG = "DBTestActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dbtest);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d(TAG, FoodListDataAccess.TABLE_CREATE);


        Log.d(TAG, "===== Food List Data Access =====\n");
        FoodListDataAccess da = new FoodListDataAccess(this);
        Date now = new Date();
        FoodList list1 = new FoodList("Mexican Cuisine", now, now);
        FoodList list2 = new FoodList("Italian Favorites", now, now);
        FoodList list3 = new FoodList("Spicy Dishes", now, now);

        da.insertFoodList(list1);
        da.insertFoodList(list2);
        da.insertFoodList(list3);
        FoodList fl = new FoodList("The Food List", new Date(), new Date());
        da.insertFoodList(fl);

        Log.d(TAG, da.getAllFoodLists().toString());
        Log.d(TAG, "========== Get all Food Lists ==========\n");
        Log.d(TAG, da.getAllFoodLists().toString());

        Log.d(TAG, "========== Get Food List by id ==========\n");
        Log.d(TAG, da.getFoodListById(list2.getId()).toString());

        Log.d(TAG, "========== Update Food List ==========\n");
        FoodList fl1 = da.getFoodListById(list2.getId());
        fl1.setListName("SOME NEW Food List Name. YES!!!");
        da.updateFoodList(fl1);
        Log.d(TAG, da.getFoodListById(list2.getId()).toString());
        Log.d(TAG, da.getAllFoodLists().toString());
        Log.d(TAG, "========== Delete Food List==========\n");
        Log.d(TAG, "====== before delete ======");
        Log.d(TAG, da.getAllFoodLists().toString());
        Log.d(TAG, "====== after delete ======");
        Log.d(TAG, "Deleted:" + da.deleteFoodList(fl) + "");
        Log.d(TAG, da.getAllFoodLists().toString());
        da.deleteFoodList(list1);
        da.deleteFoodList(list2);
        da.deleteFoodList(list3);



        Log.d(TAG, " \n\n===== Food Data Access =====");
        FoodDataAccess fda = new FoodDataAccess(this);
        Food f = new Food(3,6,1,2,7,"",false,"Ramen","Beef flavored cup ramen from the grocery store");
        Food food1 = new Food(4, 6, 0, 0, 5, "college cafeteria", false, "Meatloaf", "it's meatloaf");
        Food food2 = new Food(4, 2, 3, 1, 5, "Italy", true, "Pizza", "Classic Italian pizza with cheese and tomato.");
        Food food3 = new Food(2, 3, 0, 4, 6, "Thailand", true, "Tom Yum Soup", "Spicy Thai soup with shrimp.");

        fda.insertFood(food1);
        fda.insertFood(food2);
        fda.insertFood(food3);
        fda.insertFood(f);
        Log.d(TAG, " \n========== Get all Foods ==========");
        Log.d(TAG, fda.getAllFoods().toString());
        Log.d(TAG, f.toString());

        Log.d(TAG, " \n========== Get Food by id ==========");
        Log.d(TAG, fda.getFoodById(food1.getId()).toString());

        Log.d(TAG, " \n========== Update Food ==========");
        Food f1 = fda.getFoodById(food1.getId());
        f1.setDescription("SOME NEW Food DESCRIPTION. YES!!!");
        fda.updateFood(f1);
        Log.d(TAG, fda.getFoodById(food1.getId()).toString());

        Log.d(TAG, " \n========== Delete Food ==========");
        Log.d(TAG, "====== before delete ======");
        Log.d(TAG, fda.getAllFoods().toString());
        Log.d(TAG, "====== after delete ======");
        Log.d(TAG, "Deleted:" + fda.deleteFood(f) + "");
        Log.d(TAG, fda.getAllFoods().toString());
        fda.deleteFood(food1);
        fda.deleteFood(food2);
        fda.deleteFood(food3);

        // ========== Junction Table Tests ==========

        Log.d(TAG, "\n========== JUNCTION TABLE TESTING ==========");

        FoodList junctionTestList = new FoodList("Asian Dishes", now, now);

        da.insertFoodList(junctionTestList);

        Food junctionFood1 = new Food(3, 5, 1, 2, 6, "Japan", false, "Sushi", "Rice with fish");
        Food junctionFood2 = new Food(4, 4, 2, 1, 7, "Korea", true, "Kimchi", "Fermented spicy cabbage");

        fda.insertFood(junctionFood1);
        fda.insertFood(junctionFood2);

// Add both foods to the food list
        Log.d("Debug", "Adding Food ID " + (int)junctionFood1.getId() + " to List ID " + (int)junctionTestList.getId());
        da.addFoodToList((int)junctionFood1.getId(), (int)junctionTestList.getId());

        Log.d("Debug", "Adding Food ID " + (int)junctionFood2.getId() + " to List ID " + (int)junctionTestList.getId());
        da.addFoodToList((int)junctionFood2.getId(), (int)junctionTestList.getId());

        Log.d(TAG, "===== Foods in 'Asian Dishes' List (After Adding) =====");
        Log.d(TAG, da.getAllFoodFromFoodList((int)junctionTestList.getId()).toString());

// Delete one food from the list
        da.deleteFoodFromList((int)junctionFood1.getId(), (int)junctionTestList.getId());

        Log.d(TAG, "===== Foods in 'Asian Dishes' List (After Deletion) =====");
        Log.d(TAG, da.getAllFoodFromFoodList((int)junctionTestList.getId()).toString());

// Cleanup
        da.deleteFoodList(junctionTestList);
        fda.deleteFood(junctionFood1);
        fda.deleteFood(junctionFood2);
    }
}