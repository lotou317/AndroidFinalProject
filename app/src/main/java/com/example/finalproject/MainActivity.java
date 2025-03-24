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
import com.example.finalproject.models.MySQLiteHelper;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d(TAG, FoodListDataAccess.TABLE_CREATE);

        Log.d(TAG, "===== Food List Data Access =====\n");
        FoodListDataAccess da = new FoodListDataAccess(this);
        FoodList fl = new FoodList("The Food List", new Date(), new Date());
        da.insertFoodList(fl);

        Log.d(TAG, da.getAllFoodLists().toString());
        Log.d(TAG, "========== Get all Food Lists ==========\n");
        Log.d(TAG, da.getAllFoodLists().toString());

        Log.d(TAG, "========== Get Food List by id ==========\n");
        Log.d(TAG, da.getFoodListById(1).toString());

        Log.d(TAG, "========== Update Food List ==========\n");
        FoodList fl1 = da.getFoodListById(3);
        fl1.setListName("SOME NEW Food List Name. YES!!!");
        da.updateFoodList(fl1);
        Log.d(TAG, da.getFoodListById(1).toString());

        Log.d(TAG, "========== Delete Food List==========\n");
        Log.d(TAG, "====== before delete ======");
        Log.d(TAG, da.getAllFoodLists().toString());
        Log.d(TAG, "====== after delete ======");
        Log.d(TAG, "Deleted:" + da.deleteFoodList(fl) + "");
        Log.d(TAG, da.getAllFoodLists().toString());



        Log.d(TAG, " \n\n===== Food Data Access =====");
        FoodDataAccess fda = new FoodDataAccess(this);
        Food f = new Food(3,6,1,2,7,"",false,"Ramen","Beef flavored cup ramen from the grocery store");

        fda.insertFood(f);
        Log.d(TAG, " \n========== Get all Foods ==========");
        Log.d(TAG, fda.getAllFoods().toString());
        Log.d(TAG, f.toString());

        Log.d(TAG, " \n========== Get Food by id ==========");
        Log.d(TAG, fda.getFoodById(1).toString());

        Log.d(TAG, " \n========== Update Food ==========");
        Food f1 = fda.getFoodById(1);
        f1.setDescription("SOME NEW Food DESCRIPTION. YES!!!");
        fda.updateFood(f1);
        Log.d(TAG, fda.getFoodById(1).toString());

        Log.d(TAG, " \n========== Delete Food ==========");
        Log.d(TAG, "====== before delete ======");
        Log.d(TAG, fda.getAllFoods().toString());
        Log.d(TAG, "====== after delete ======");
        Log.d(TAG, "Deleted:" + fda.deleteFood(f) + "");
        Log.d(TAG, fda.getAllFoods().toString());

    }
}