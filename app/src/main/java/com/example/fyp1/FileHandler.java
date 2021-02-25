package com.example.fyp1;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileHandler {

    public static final String DAILIES_FILENAME = "daily_tasks.dat";
    public static final String TO_DO_FILENAME = "to_do_tasks.dat";

    public static void writeDailiesData(ArrayList<String> tasks, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(DAILIES_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tasks);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readDailiesData(Context context){
        ArrayList<String> taskList = null;
        try {
            FileInputStream fis = context.openFileInput(DAILIES_FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            taskList = (ArrayList<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            taskList = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return taskList;
    }

    public static void writeToDoData(ArrayList<String> tasks, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(TO_DO_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tasks);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readToDoData(Context context){
        ArrayList<String> taskList = null;
        try {
            FileInputStream fis = context.openFileInput(TO_DO_FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            taskList = (ArrayList<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            taskList = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return taskList;
    }
}
