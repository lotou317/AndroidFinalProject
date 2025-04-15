package com.example.finalproject;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.dataaccess.FoodDataAccess;
import com.example.finalproject.dataaccess.FoodListDataAccess;
import com.example.finalproject.models.Food;
import com.example.finalproject.models.FoodList;

public class FoodListDetailsActivity extends AppCompatActivity {
    public static final String TAG = "FoodListDetailsActivity";
    public static final String EXTRA_FOOD_LIST_ID = "foodListId";
    FoodListDataAccess flda;
    FoodList foodList;

    Button btnSave;
    Button btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food_list_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}