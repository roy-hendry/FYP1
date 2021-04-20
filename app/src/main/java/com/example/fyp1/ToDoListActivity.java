package com.example.fyp1;

import androidx.appcompat.app.AppCompatActivity; // importing required packages
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ToDoListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private Button newToDoTaskButton; // declaring private views (interfaces)
    private EditText toDoListEditText;
    private ListView toDoList;

    private Button dailiesPageButton;
    private Button shopPageButton;
    private Button combatPageButton;

    private TextView healthTextView;
    private TextView goldTextView;
    private int healthValue;
    private int goldValue;

    public static final String SHARED_PREFERENCES = "sharedPreferences"; // creating static values (values that can't be changed) so that the shared preference can use them
    public static final String HEALTH_VALUE = "healthValue";
    public static final String GOLD_VALUE = "goldValue";

    private ArrayList<String> tasks; // declaring a private ArrayLists for the user's information to be stored
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list); // link up the activity_to_do_list.xml file to this file as the corresponding file

        toDoListEditText = findViewById(R.id.toDoListEditText); // attaching the xml interfaces to the interfaces we made above
        newToDoTaskButton = findViewById(R.id.newToDoTaskButton);
        toDoList = findViewById(R.id.toDoList);
        dailiesPageButton = findViewById(R.id.dailiesPageButton);
        shopPageButton = findViewById(R.id.shopPageButton);
        combatPageButton = findViewById(R.id.combatPageButton);
        healthTextView = findViewById(R.id.healthValueTextView);
        goldTextView = findViewById(R.id.goldValueTextView);

        tasks = FileHandler.readToDoData(this); // calls the readToDoData method inside the FileHandler to recall data written from the appropriate file and reload it
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks); // create a new ArrayAdapter that uses a simple list based off the tasks
        toDoList.setAdapter(adapter); // setting the new ArrayAdapter to correspond with the ListView of tasks

        healthTextView.setText(String.valueOf(healthValue)); // Setting the text views so that they show item values
        goldTextView.setText(String.valueOf(goldValue));

        newToDoTaskButton.setOnClickListener(this); // setting buttons to have onClick capabilities
        toDoList.setOnItemClickListener(this);
        toDoList.setOnItemLongClickListener(this); // onLongClick capabilities for the ListView (click and hold for over a second)

        // when clicked it will call the OpenDailiesActivity method
        dailiesPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  { openDailiesActivity(); }
        });

        // when clicked it will call the OpenShopActivity method
        shopPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  { openShopActivity(); }
        });

        // when clicked it will call the OpenCombatActivity method
        combatPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  { openCombatActivity(); }
        });

        loadData();
    }

    /**
     * This method will be called when a user clicks the newDailyButton when clicked will take the
     * string inside the EditText box known as "taskEditText" and it will check if the string is
     * empty if all of the spaces are taken out.
     * If the string is not empty then set the text box
     * to empty, create a new task with the string from the EditText box and write the value of the
     * new task to the toDoList task file then show a toast saying "Task Added".
     * If the box is empty when trimmed then show a toast saying "Please enter a task".
     *
     * @param  v  The view of the button being clicked
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newToDoTaskButton:
                String taskEntered = toDoListEditText.getText().toString();
                if (!taskEntered.trim().isEmpty()) { //if the trimmed input (input without spaces) is not empty then
                    //continue down this flow
                    adapter.add(taskEntered); //make it so they can't enter something with a length of less than 1 or something with no "characters"
                    toDoListEditText.setText("");
                    FileHandler.writeToDoData(tasks, this);
                    Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
                    break;
                }
                else {
                    //otherwise go to this flow
                    Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show();
                }
        }
    }

    /**
     * When called this method will remove the task corresponding with it's position and then
     * write the updated tasks ArrayList to the ToDoList file. The user is rewarded by giving them
     * 5 gold for completing the task. A toast is shown saying "Task Completed".
     *
     * @param  parent  The parent adapter view
     * @param  view  The current view
     * @param  position  The Integer position of the item clicked (the first being 0)
     * @param  id  The id given to the item clicked
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        tasks.remove(position);

        adapter.notifyDataSetChanged();
        FileHandler.writeToDoData(tasks, this);
        goldValue = goldValue + 5;
        goldTextView.setText(String.valueOf(goldValue));
        Toast.makeText(this, "Task Completed", Toast.LENGTH_SHORT).show();
    }

    /**
     * When called this method will remove the task corresponding with the position
     * of the item clicked and held. The new tasks ArrayList values are written
     * to the file to update it now that the change have been made. A toast is made to show
     * "Tasks Removed". A boolean value is returned. This is just due to onItemLongClick
     * needing to be a boolean type method so any boolean value is required to be returned.
     *
     * @param  parent  The parent adapter view
     * @param  view  The current view
     * @param  position  The Integer position of the item clicked (the first being 0)
     * @param  id  The id given to the item clicked
     * @return true there is a clash if this onItemLongClick doesn't return a boolean value
     * so we make it return any boolean value
     */
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        tasks.remove(position);

        adapter.notifyDataSetChanged();
        FileHandler.writeToDoData(tasks, this);
        Toast.makeText(this, "Task Removed", Toast.LENGTH_SHORT).show();
        return true;
    }

    /**
     * When called this method will create a new set of shared preferences that will enable
     * it to save values of the variables held in it and commit them to preferences
     */
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(HEALTH_VALUE, healthValue);
        editor.putInt(GOLD_VALUE, goldValue);

        editor.commit();
    }

    /**
     * This method will call saveData and call the startActivity method and pass the intent to it this will let it
     * know which page to go to, in this case that will be the DailyTasksActivity page
     */
    public void openDailiesActivity() {
        saveData();
        Intent intent = new Intent(this, DailyTasksActivity.class);
        startActivity(intent);
    }

    /**
     * This method will call saveData and call the startActivity method and pass the intent to it this will let it
     * know which page to go to, in this case that will be the Shop page
     */
    public void openShopActivity() {
        saveData();
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }

    /**
     * This method will call saveData and call the startActivity method and pass the intent to it this will let it
     * know which page to go to, in this case that will be the Combat page
     */
    public void openCombatActivity() {
        saveData();
        Intent intent = new Intent(this, CombatActivity.class);
        startActivity(intent);
    }

    /**
     * When called this method will load up the values previously set from the saveData method
     * if the variables have no previously stored preference then the defaults will be as shown
     * below
     */
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);

        healthValue = sharedPreferences.getInt(HEALTH_VALUE, 100);
        healthTextView.setText(String.valueOf(healthValue));

        goldValue = sharedPreferences.getInt(GOLD_VALUE, 0);
        goldTextView.setText(String.valueOf(goldValue));
    }

}
