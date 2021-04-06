package com.example.fyp1;

import android.content.Context; // importing required packages
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileHandler {

    public static final String DAILIES_FILENAME = "daily_tasks.txt"; // making the names of each filename static final values as they won't be changed
    public static final String TO_DO_FILENAME = "to_do_tasks.txt";
    public static final String CHECKBOX_STATE_FILENAME = "checkbox_states.txt";

    /**
     * Writes the ArrayList to the file name "DAILIES_FILENAME" in a private file.
     *
     * @param  tasks  the ArrayList holding all of the task Strings
     * @param  context the context in which the method is being called from
     */
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

    /**
     * Reads the ArrayList from the file name "DAILIES_FILENAME". Then returns the ArrayList
     * that was read.
     *
     * @param  context the context in which the method is being called from
     * @return  taskList the ArrayList from the file
     */
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

    /**
     * Writes the ArrayList to the file name "CHECKBOX_STATE_FILENAME" in a private file.
     *
     * @param  checkboxStateArrayList  the ArrayList holding all of the task Strings
     * @param  context the context in which the method is being called from
     */
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

    /**
     * Reads the ArrayList from the file name "DAILIES_FILENAME". Then returns the ArrayList
     * that was read.
     *
     * @param  context the context in which the method is being called from
     * @return  taskList the ArrayList from the file
     */
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

    /**
     * Writes the ArrayList to the file name "TO_DO_FILENAME" in a private file.
     *
     * @param  tasks  the ArrayList holding all of the task Strings
     * @param  context the context in which the method is being called from
     */
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

    /**
     * Reads the ArrayList from the file name "TO_DO_FILENAME". Then returns the ArrayList
     * that was read.
     *
     * @param  context the context in which the method is being called from
     * @return  taskList the ArrayList from the file
     */
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
