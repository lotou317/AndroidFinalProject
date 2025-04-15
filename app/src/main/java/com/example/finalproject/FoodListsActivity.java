package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.dataaccess.FoodDataAccess;
import com.example.finalproject.dataaccess.FoodListDataAccess;
import com.example.finalproject.models.Food;
import com.example.finalproject.models.FoodList;

import java.util.ArrayList;

public class FoodListsActivity extends AppCompatActivity {
    public static final String TAG = "FoodListsActivity";

    private ListView lsFoodLists;
    private FoodListDataAccess flda;
    private ArrayList<FoodList> allFoodLists;
    private Button btnAddFoodList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food_lists);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAddFoodList = findViewById(R.id.btnAddFoodList);
        btnAddFoodList.setOnClickListener(new View.OnClickListener() { // creating a new instance or sub class of the OnClickListener interface
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodListsActivity.this, FoodListDetailsActivity.class );
                startActivity(i);
            }
        });
        lsFoodLists = findViewById(R.id.lsFoodLists);
        flda = new FoodListDataAccess(this);
        allFoodLists = flda.getAllFoodLists();
        if(allFoodLists.isEmpty()){
            startActivity(new Intent(this, FoodDetailsActivity.class));
        }

        ArrayAdapter<FoodList> adapter = new ArrayAdapter<>(this, R.layout.custom_foodlist_list_item, R.id.foodListName, allFoodLists){ // made a subclass of the array adapter and over rode the getView method, having the original functionality, while including more custom stuff we wanted the method to do
            @Override
            public View getView(int position, View convertView, ViewGroup parentListView){
                View listItemView = super.getView(position, convertView, parentListView); //returns the view for the row

                TextView foodListTitle = listItemView.findViewById(R.id.foodListName);
                TextView foodListCreatedDate = listItemView.findViewById(R.id.dateCreated);
                TextView foodListUpdatedDate = listItemView.findViewById(R.id.dateUpdated);

                FoodList currentFoodList = allFoodLists.get(position);

                //data binding
                foodListTitle.setText(currentFoodList.getListName());
                String createdDateStr = null;
                int cy = currentFoodList.getFirstCreated().getYear() + 1900;
                int cm = currentFoodList.getFirstCreated().getMonth();
                int cd = currentFoodList.getFirstCreated().getDate();
                String cmStr = "0";
                String cdStr = "0";
                if((cm+1) < 10){
                    cmStr = "0"+(cm+1);
                }else {
                    cmStr = "" + (cm+1);
                }
                if((cd) < 10){
                    cdStr = "0"+(cd);
                }else {
                    cdStr = "" + (cd);
                }
                String cDate = cmStr + "/" + cdStr + "/" + cy;
                createdDateStr = "Created: " + cDate;
                foodListCreatedDate.setText(createdDateStr);

                String updatedDateStr = null;
                int uy = currentFoodList.getLastUpdated().getYear() + 1900;
                int um = currentFoodList.getLastUpdated().getMonth();
                int ud = currentFoodList.getLastUpdated().getDate();
                String umStr = "0";
                String udStr = "0";
                if((um+1) < 10){
                    umStr = "0"+(um+1);
                }else {
                    umStr = "" + (um+1);
                }
                if((ud) < 10){
                    udStr = "0"+(ud);
                }else {
                    udStr = "" + (ud);
                }
                String uDate = umStr + "/" + udStr + "/" + uy;
                updatedDateStr = "Updated: " + uDate;
                foodListUpdatedDate.setText(updatedDateStr);

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
                        Log.d(TAG, "i: " + currentFoodList.getId());
//                        Task selectedTask = allTasks.get(currentTask.getId()); // this is getting the task by the index number
                        Log.d(TAG, currentFoodList.toString());
//                Log.d(TAG, da.getTaskById(i+1).toString()); // doing this isn't recommended as the i value/ index value of where the item is in the list can change as items get added and or deleted
                        Intent intent = new Intent(FoodListsActivity.this, FoodListDetailsActivity.class);
                        intent.putExtra(FoodListDetailsActivity.EXTRA_FOOD_LIST_ID, currentFoodList.getId());
                        startActivity(intent);
                    }
                });

                return listItemView;
            }

        }; //using android.R.layout give you all the layouts which are given/built in android to use
        lsFoodLists.setAdapter(adapter);


    }
}