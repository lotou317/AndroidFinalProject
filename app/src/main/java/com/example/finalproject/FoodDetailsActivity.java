package com.example.finalproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.dataaccess.FoodDataAccess;
import com.example.finalproject.models.Food;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FoodDetailsActivity extends FoodMenuActivity {
    public static final String TAG = "FoodDetailsActivity";
    public static final String EXTRA_FOOD_ID = "foodId";
    FoodDataAccess fda;
    Food food;

    SeekBar sweetValue;
    TextView sliderSweetValueText;
    SeekBar saltyValue;
    TextView sliderSaltyValueText;
    SeekBar sourValue;
    TextView sliderSourValueText;
    SeekBar bitterValue;
    TextView sliderBitterValueText;
    SeekBar umamiValue;
    TextView sliderUmamiValueText;
    EditText txtCountryOfOrigin;
    CheckBox isSpicy;
    EditText txtName;
    EditText txtDescription;
    Button btnSave;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtDescription = findViewById(R.id.txtDescription);
        txtName = findViewById(R.id.foodName);
        isSpicy = findViewById(R.id.isSpicy);
        sweetValue = findViewById(R.id.seekBarSweet);
        saltyValue = findViewById(R.id.seekBarSalty);
        sourValue = findViewById(R.id.seekBarSour);
        bitterValue = findViewById(R.id.seekBarBitter);
        umamiValue = findViewById(R.id.seekBarUmami);
        txtCountryOfOrigin = findViewById(R.id.txtCountryOfOrigin);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        fda = new FoodDataAccess(this);

        Intent i = getIntent();
        long id = i.getLongExtra(EXTRA_FOOD_ID, 0);
        if(id > 0){
//            Log.d(TAG, "TODO Get item with id of " + id);
            food = fda.getFoodById(id);
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

        sweetValue = findViewById(R.id.seekBarSweet);
        sliderSweetValueText = findViewById(R.id.sliderSweetValueText);

        // Set initial value
        sliderSweetValueText.setText("Value: " + sweetValue.getProgress());

        // Listener for changes
        sweetValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sliderSweetValueText.setText("Value: " + progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        saltyValue = findViewById(R.id.seekBarSalty);
        sliderSaltyValueText = findViewById(R.id.sliderSaltyValueText);

        // Set initial value
        sliderSaltyValueText.setText("Value: " + saltyValue.getProgress());

        // Listener for changes
        saltyValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sliderSaltyValueText.setText("Value: " + progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sourValue = findViewById(R.id.seekBarSour);
        sliderSourValueText = findViewById(R.id.sliderSourValueText);

        // Set initial value
        sliderSourValueText.setText("Value: " + sourValue.getProgress());

        // Listener for changes
        sourValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sliderSourValueText.setText("Value: " + progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        bitterValue = findViewById(R.id.seekBarBitter);
        sliderBitterValueText = findViewById(R.id.sliderBitterValueText);

        // Set initial value
        sliderBitterValueText.setText("Value: " + bitterValue.getProgress());

        // Listener for changes
        bitterValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sliderBitterValueText.setText("Value: " + progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        umamiValue = findViewById(R.id.seekBarUmami);
        sliderUmamiValueText = findViewById(R.id.sliderUmamiValueText);

        // Set initial value
        sliderUmamiValueText.setText("Value: " + umamiValue.getProgress());

        // Listener for changes
        umamiValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sliderUmamiValueText.setText("Value: " + progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }

        private void putDataIntoUI() {
            if(food != null){
                txtName.setText(food.getName());
                txtDescription.setText(food.getDescription());
                isSpicy.setChecked(food.isSpicy());
                sweetValue.setProgress(food.getSweet());
                saltyValue.setProgress(food.getSalty());
                sourValue.setProgress(food.getSour());
                bitterValue.setProgress(food.getBitter());
                umamiValue.setProgress(food.getUmami());
                txtCountryOfOrigin.setText(food.getCountryOfOrigin());

            }
        }

        private boolean validate(){
            boolean isValid = false;

            // validate the Description
            if(txtDescription.getText().toString().isEmpty()){
                txtDescription.setError("You must enter a description");
            }

            // validate the Description
            if(txtName.getText().toString().isEmpty()){
                txtName.setError("You must enter a name");
            }

            if(!(txtDescription.getText().toString().isEmpty()) && !(txtName.getText().toString().isEmpty())){
                isValid = true;
            }

            return isValid;
        }

        private boolean save(){
            try {
                if (validate()) {
                    getDataFromUI();
                    if (food.getId() > 0) {
                        try {
                            fda.updateFood(food);
                        } catch (Exception e) {
                            Log.d(TAG, "Unable to update food");
                        }
                    } else {
                        try {
                            fda.insertFood(food);
                        } catch (Exception e) {
                            Log.d(TAG, "Unable to insert food");
                        }
                    }
                    Intent intent = new Intent(FoodDetailsActivity.this, FoodsActivity.class);
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
            String name = txtName.getText().toString();
            String desc = txtDescription.getText().toString();
            boolean spicy = isSpicy.isChecked();
            int sweet = sweetValue.getProgress();
            int salty = saltyValue.getProgress();
            int sour = sourValue.getProgress();
            int bitter = bitterValue.getProgress();
            int umami = umamiValue.getProgress();
            String countryOfOrigin = txtCountryOfOrigin.getText().toString();

            if(food != null){
                food.setName(name);
                food.setDescription(desc);
                food.setSpicy(spicy);
                food.setSweet(sweet);
                food.setSalty(salty);
                food.setSour(sour);
                food.setBitter(bitter);
                food.setUmami(umami);
                food.setCountryOfOrigin(countryOfOrigin);
            }else{
                food = new Food(sweet, salty, sour, bitter, umami,countryOfOrigin, spicy, name, desc);
            }

        }

        private void showDeleteDialog(){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(R.string.delete_task);
            alert.setMessage(R.string.confirm_delete);
            alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    fda.deleteFood(food);
                    startActivity(new Intent(FoodDetailsActivity.this, FoodsActivity.class));
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
