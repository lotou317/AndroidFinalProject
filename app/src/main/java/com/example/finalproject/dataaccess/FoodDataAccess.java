package com.example.finalproject.dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalproject.models.MySQLiteHelper;

public class FoodDataAccess {
    public static final String TAG = "FoodDataAccess";

    public static final String COLUMN_LAST_UPDATED = "lastUpdated";
//    public static ArrayList<Task> allTasks = new ArrayList<Task>(){{
//        add(new Task(1, "Mow the lawn", new Date(), false));
//        add(new Task(2, "Take out the trash", new Date(), false));
//        add(new Task(3, "Pay rent", new Date(), true));
//    }};
//
//    private Context context;
//
//    public TaskDataAccess(Context c){
//        this.context = c;
//    }
//
//    public ArrayList<Task> getAllTasks(){
//        return allTasks;
//    }
//
//    public Task getTaskById(long id){
//        for(Task t: allTasks){
//            if(t.getId() == id){
//                return t;
//            }
//        }
//        return null;
//    }
//
//    private long getMaxId(){
//        long maxId = allTasks.get(0).getId();
//        for(Task t: allTasks){
//            maxId = t.getId() > maxId ? t.getId() : maxId; // could be replaced with "maxId = Math.max(t.getId(), maxId);"
//        }
//        return maxId;
//    }
//
//    public Task insertTask(Task t)throws Exception{
//        if(t.isValid()){
//            allTasks.add(t);
//            t.setId(getMaxId()+1);
//            return t;
//        }
//        throw new Exception("Invalid Task on insert");
//    }
//
//    public Task updateTask(Task updatedTask)throws Exception{
//        if(updatedTask.isValid()){
//            Task taskToUpdate = getTaskById(updatedTask.getId());
//            taskToUpdate.setDescription(updatedTask.getDescription());
//            taskToUpdate.setDue(updatedTask.getDue());
//            taskToUpdate.setDone(updatedTask.isDone());
//            return updatedTask;
//        }
//        throw new Exception("Invalid Task on update");
//    }
//
//    public int deleteTask(Task taskToDelete){
//        for(Task t: allTasks){
//            if(t.getId() == taskToDelete.getId()){
//                allTasks.remove(t);
//                return 1;
//            }
//        }
//        return 0;
//    }



//    CREATE TABLE Food (
//            id INTEGER PRIMARY KEY AUTOINCREMENT,
//            sweet INTEGER NOT NULL,
//            salty INTEGER NOT NULL,
//            sour INTEGER NOT NULL,
//            bitter INTEGER NOT NULL,
//            umami INTEGER NOT NULL,
//            countryOfOrigin TEXT,
//            spicy BOOLEAN NOT NULL DEFAULT 0,
//            name TEXT NOT NULL,
//            description TEXT
//    );

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
}
