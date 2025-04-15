package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.dataaccess.FoodDataAccess;
import com.example.finalproject.models.Food;

import java.util.ArrayList;

public class FoodsActivity extends AppCompatActivity {

    public static final String TAG = "FoodsActivity";

    private ListView lsFoods;
    private FoodDataAccess fda;
    private ArrayList<Food> allFoods;
    private Button btnAddFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_foods);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAddFood = findViewById(R.id.btnAddFood);
        btnAddFood.setOnClickListener(new View.OnClickListener() { // creating a new instance or sub class of the OnClickListener interface
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodsActivity.this, FoodDetailsActivity.class );
                startActivity(i);
            }
        });
        lsFoods = findViewById(R.id.lsFoods);
        fda = new FoodDataAccess(this);
        allFoods = fda.getAllFoods();
        if(allFoods.isEmpty()){
            startActivity(new Intent(this, FoodDetailsActivity.class));
        }

        ArrayAdapter<Food> adapter = new ArrayAdapter<>(this, R.layout.custom_food_list_item, R.id.foodName, allFoods){ // made a subclass of the array adapter and over rode the getView method, having the original functionality, while including more custom stuff we wanted the method to do
            @Override
            public View getView(int position, View convertView, ViewGroup parentListView){
                View listItemView = super.getView(position, convertView, parentListView); //returns the view for the row

                TextView foodTitle = listItemView.findViewById(R.id.foodName);
                TextView foodDesc = listItemView.findViewById(R.id.foodDescription);

                Food currentFood = allFoods.get(position);

                //data binding
                foodTitle.setText(currentFood.getName());
                foodDesc.setText(currentFood.getDescription());

                //setting a click handler
//                chkActive.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        currentTask.setDone(chkActive.isChecked());
//                        try {
//                            da.updateTask(currentTask);
//                        } catch (Exception e) {
//                            throw new RuntimeException(e);
//                        }
//                        Log.d(TAG, currentTask.toString());
//                    }
//                });

                //setting a click handler ot go back to the main page
                listItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick( View view) {
                        Log.d(TAG, "i: " + currentFood.getId());
//                        Task selectedTask = allTasks.get(currentTask.getId()); // this is getting the task by the index number
                        Log.d(TAG, currentFood.toString());
//                Log.d(TAG, da.getTaskById(i+1).toString()); // doing this isn't recommended as the i value/ index value of where the item is in the list can change as items get added and or deleted
                        Intent intent = new Intent(FoodsActivity.this, FoodDetailsActivity.class);
                        intent.putExtra(FoodDetailsActivity.EXTRA_FOOD_ID, currentFood.getId());
                        startActivity(intent);
                    }
                });

                return listItemView;
            }

        }; //using android.R.layout give you all the layouts which are given/built in android to use
        lsFoods.setAdapter(adapter);


    }
}