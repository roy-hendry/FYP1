package com.example.fyp1;

import android.content.Context;
import android.widget.CheckedTextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class FileHandler {

    public static final String DAILIES_FILENAME = "daily_tasks.txt";
    public static final String TO_DO_FILENAME = "to_do_tasks.txt";
    public static final String CHECKBOX_STATE_FILENAME = "checkbox_states.txt";

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


    public static void setUpCheckbox(ArrayList<String> checkboxState, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(CHECKBOX_STATE_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(checkboxState);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setCheckboxValue(ArrayList<String> checkboxStateArrayList, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(CHECKBOX_STATE_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(checkboxStateArrayList);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readCheckboxData(Context context){
        ArrayList<String> checkboxList = null;
        try {
            FileInputStream fis = context.openFileInput(CHECKBOX_STATE_FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            checkboxList = (ArrayList<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            checkboxList = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return checkboxList;
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
