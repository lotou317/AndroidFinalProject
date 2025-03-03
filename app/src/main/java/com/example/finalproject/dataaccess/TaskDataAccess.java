package com.example.finalproject.dataaccess;

import android.content.Context;

import com.example.taskapp.models.Task;

import java.util.ArrayList;
import java.util.Date;

public class TaskDataAccess {
    public static final String TAG = "TaskDataAccess";
    public static ArrayList<Task> allTasks = new ArrayList<Task>(){{
        add(new Task(1, "Mow the lawn", new Date(), false));
        add(new Task(2, "Take out the trash", new Date(), false));
        add(new Task(3, "Pay rent", new Date(), true));
    }};

    private Context context;

    public TaskDataAccess(Context c){
        this.context = c;
    }

    public ArrayList<Task> getAllTasks(){
        return allTasks;
    }

    public Task getTaskById(long id){
        for(Task t: allTasks){
            if(t.getId() == id){
                return t;
            }
        }
        return null;
    }

    private long getMaxId(){
        long maxId = allTasks.get(0).getId();
        for(Task t: allTasks){
            maxId = t.getId() > maxId ? t.getId() : maxId; // could be replaced with "maxId = Math.max(t.getId(), maxId);"
        }
        return maxId;
    }

    public Task insertTask(Task t)throws Exception{
        if(t.isValid()){
            allTasks.add(t);
            t.setId(getMaxId()+1);
            return t;
        }
        throw new Exception("Invalid Task on insert");
    }

    public Task updateTask(Task updatedTask)throws Exception{
        if(updatedTask.isValid()){
            Task taskToUpdate = getTaskById(updatedTask.getId());
            taskToUpdate.setDescription(updatedTask.getDescription());
            taskToUpdate.setDue(updatedTask.getDue());
            taskToUpdate.setDone(updatedTask.isDone());
            return updatedTask;
        }
        throw new Exception("Invalid Task on update");
    }

    public int deleteTask(Task taskToDelete){
        for(Task t: allTasks){
            if(t.getId() == taskToDelete.getId()){
                allTasks.remove(t);
                return 1;
            }
        }
        return 0;
    }

}
