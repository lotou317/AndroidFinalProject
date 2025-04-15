package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Date;

public class FoodListDetailsActivity extends FoodMenuActivity {
    public static final String TAG = "FoodListDetailsActivity";
    public static final String EXTRA_FOOD_LIST_ID = "foodListId";
    FoodListDataAccess flda;
    FoodDataAccess fda;
    private ArrayList<Food> allFoodsInList;
    private ArrayList<Food> allFoodsOutOfList;
    FoodList foodList;
    EditText foodListName;
    TextView txtCreated;
    TextView txtLastUpdated;
    TextView txtLsAddFoods;
    TextView txtLsFoods;
    private ListView lsFoods;
    private ListView lsAddFoods;
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


        flda = new FoodListDataAccess(this);

        foodListName = findViewById(R.id.foodListName);
        txtCreated = findViewById(R.id.txtCreated);
        txtLastUpdated = findViewById(R.id.txtLastUpdated);
        lsFoods = findViewById(R.id.lsRemoveFoods);
        lsAddFoods = findViewById(R.id.lsAddFoods);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        txtLsAddFoods = findViewById(R.id.txtLsAddFoods);
        txtLsFoods = findViewById(R.id.txtLsFoods);

        Intent i = getIntent();
        long id = i.getLongExtra(EXTRA_FOOD_LIST_ID, 0);
        if(id > 0){
//            Log.d(TAG, "TODO Get item with id of " + id);
            foodList = flda.getFoodListById(id);
//            Log.d(TAG, task.toString());
            putDataIntoUI();
            btnDelete.setVisibility(View.VISIBLE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
                save();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteDialog();
            }
        });


        lsFoods = findViewById(R.id.lsRemoveFoods);
        fda = new FoodDataAccess(this);
        if(foodList == null){
            lsFoods.setVisibility(View.GONE);
            txtLsFoods.setVisibility(View.GONE);
            lsAddFoods.setVisibility(View.GONE);
            txtLsAddFoods.setVisibility(View.GONE);
        }else{
            allFoodsInList = flda.getAllFoodFromFoodList((int)foodList.getId());

            ArrayAdapter<Food> adapter = new ArrayAdapter<>(this, R.layout.custom_foodlist_remove_item, R.id.foodName, allFoodsInList){ // made a subclass of the array adapter and over rode the getView method, having the original functionality, while including more custom stuff we wanted the method to do
                @Override
                public int getCount() {
                    // If the list is empty, pretend there is 1 item (for placeholder)
                    return allFoodsInList.isEmpty() ? 1 : allFoodsInList.size();
                }

                @Override
                public boolean isEnabled(int position) {
                    // Disable click interactions if list is empty
                    return !allFoodsInList.isEmpty();
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View listItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_foodlist_remove_item, parent, false);

                    TextView foodTitle = listItemView.findViewById(R.id.foodName);
                    Button removeFoodBtn = listItemView.findViewById(R.id.btn_food_remove);

                    if (allFoodsInList.isEmpty()) {
                        foodTitle.setText("No foods in this list yet.");
                        removeFoodBtn.setVisibility(View.GONE);
                    } else {
                        Food currentFood = allFoodsInList.get(position);
                        foodTitle.setText(currentFood.getName());

                        removeFoodBtn.setVisibility(View.VISIBLE);
                        removeFoodBtn.setOnClickListener(v -> {
                            try {
                                fda.deleteFoodFromList((int) currentFood.getId(), (int) foodList.getId());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                            allFoodsInList.remove(position);
                            notifyDataSetChanged();
                        });
                        listItemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick( View view) {
                                Log.d(TAG, "i: " + currentFood.getId());
//                        Task selectedTask = allTasks.get(currentTask.getId()); // this is getting the task by the index number
                                Log.d(TAG, currentFood.toString());
//                Log.d(TAG, da.getTaskById(i+1).toString()); // doing this isn't recommended as the i value/ index value of where the item is in the list can change as items get added and or deleted
                                Intent intent = new Intent(FoodListDetailsActivity.this, FoodDetailsActivity.class);
                                intent.putExtra(FoodDetailsActivity.EXTRA_FOOD_ID, currentFood.getId());
                                startActivity(intent);
                            }
                        });
                    }

                    return listItemView;
                }

            }; //using android.R.layout give you all the layouts which are given/built in android to use
            lsFoods.setAdapter(adapter);



            lsAddFoods = findViewById(R.id.lsAddFoods);
            allFoodsOutOfList = flda.getAllFoodsNotInFoodList((int)foodList.getId());
            if(allFoodsOutOfList.isEmpty()){
                Log.d(TAG, "allFoodsOutOfList is empty");
                startActivity(new Intent(this, FoodDetailsActivity.class));
            }

            ArrayAdapter<Food> adapter2 = new ArrayAdapter<>(this, R.layout.custom_foodlist_add_item, R.id.foodName, allFoodsOutOfList){ // made a subclass of the array adapter and over rode the getView method, having the original functionality, while including more custom stuff we wanted the method to do
                @Override
                public View getView(int position, View convertView, ViewGroup parentListView){
                    View listItemView = super.getView(position, convertView, parentListView); //returns the view for the row

                    TextView foodTitle = listItemView.findViewById(R.id.foodName);
                    Button addFoodBtn = listItemView.findViewById(R.id.btn_food_add);

                    Food currentFood = allFoodsOutOfList.get(position);

                    //data binding
                    foodTitle.setText(currentFood.getName());

                    //setting a click handler
                    addFoodBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            try {
                                fda.addFoodToList((int)currentFood.getId(), (int)foodList.getId());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            Log.d(TAG, currentFood.toString());
                            // Remove the item from the data list
                            allFoodsOutOfList.remove(position);

                            // Notify the adapter that the data has changed
                            notifyDataSetChanged();
                        }
                    });

                    //setting a click handler ot go back to the main page

                    return listItemView;
                }

            }; //using android.R.layout give you all the layouts which are given/built in android to use
            lsAddFoods.setAdapter(adapter2);

        }


    }// end of onCreate
    private void putDataIntoUI() {
        if(foodList != null){
            foodListName.setText(foodList.getListName());
            String createdDateStr = null;
            int cy = foodList.getFirstCreated().getYear() + 1900;
            int cm = foodList.getFirstCreated().getMonth();
            int cd = foodList.getFirstCreated().getDate();
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
            txtCreated.setText(createdDateStr);

            String updatedDateStr = null;
            int uy = foodList.getLastUpdated().getYear() + 1900;
            int um = foodList.getLastUpdated().getMonth();
            int ud = foodList.getLastUpdated().getDate();
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
            txtLastUpdated.setText(updatedDateStr);

        }
    }

    private boolean validate(){
        boolean isValid = false;

        // validate the Description
        if(foodListName.getText().toString().isEmpty()){
            foodListName.setError("You must enter a name");
        }

        if(!(foodListName.getText().toString().isEmpty())){
            isValid = true;
        }

        return isValid;
    }

    private boolean save(){
        try {
            if (validate()) {
                getDataFromUI();
                if (foodList.getId() > 0) {
                    try {
                        flda.updateFoodList(foodList);
                    } catch (Exception e) {
                        Log.d(TAG, "Unable to update food");
                    }
                } else {
                    try {
                        flda.insertFoodList(foodList);
                    } catch (Exception e) {
                        Log.d(TAG, "Unable to insert food");
                    }
                }
                Intent intent = new Intent(FoodListDetailsActivity.this, FoodListsActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        } catch(Exception e){
            Log.d(TAG, "Saving Errors");
            Log.d(TAG, e.getMessage());
        }
        return false;
    }

    private void getDataFromUI(){
        String name = foodListName.getText().toString();

        if(foodList != null){
            foodList.setListName(name);
            foodList.setLastUpdated(new Date()); // update the last updated timestamp
        }else{
            foodList = new FoodList(name, new Date(), new Date());
        }

    }

    private void showDeleteDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.delete_task);
        alert.setMessage(R.string.confirm_delete);
        alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                flda.deleteFoodList(foodList);
                startActivity(new Intent(FoodListDetailsActivity.this, FoodListsActivity.class));
            }
        });
        alert.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }

} // end of taskDetailsActivity class