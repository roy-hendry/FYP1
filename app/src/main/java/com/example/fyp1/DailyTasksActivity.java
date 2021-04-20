package com.example.fyp1;

import androidx.appcompat.app.AppCompatActivity; // importing required packages
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class DailyTasksActivity extends AppCompatActivity implements OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private Button newDailyButton; // declaring private views (interfaces)
    private Button beforeBedCashInButton;
    private EditText taskEditText;
    private ListView taskList;
    private ListView taskListState;

    private Button toDoListPageButton;
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
    private ArrayList<String> taskState;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapterSecondary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily); // link up the activity_daily.xml file to this file as the corresponding file

        taskEditText = findViewById(R.id.taskEditText); // attaching the xml interfaces to the interfaces we made above
        newDailyButton = findViewById(R.id.newDailyButton);
        beforeBedCashInButton = findViewById(R.id.cashIn);
        taskList = findViewById(R.id.taskList);
        taskListState = findViewById(R.id.taskListState);
        toDoListPageButton = findViewById(R.id.toDoListPageButton);
        shopPageButton = findViewById(R.id.shopPageButton);
        combatPageButton = findViewById(R.id.combatPageButton);
        healthTextView = findViewById(R.id.healthValueTextView);
        goldTextView = findViewById(R.id.goldValueTextView);

        tasks = FileHandler.readDailiesData(this); // calls the readDailiesData method inside the FileHandler to recall data written from the appropriate file and reload it
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks); // create a new ArrayAdapter that uses a simple list based off the tasks
        taskList.setAdapter(adapter);
        taskState = FileHandler.readCheckboxData(this);
        adapterSecondary = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskState);
        taskListState.setAdapter(adapterSecondary);

        healthTextView.setText(String.valueOf(healthValue)); // Setting the text views so that they show item values
        goldTextView.setText(String.valueOf(goldValue));

        System.out.println("tasks: "+ tasks);
        System.out.println("checkboxState: "+ taskState);

        newDailyButton.setOnClickListener(this); // setting newDailyButton have onClick capabilities
        beforeBedCashInButton.setOnClickListener(this);
        taskList.setOnItemClickListener(this);
        taskList.setOnItemLongClickListener(this); // onLongClick capabilities for the ListView (click and hold for over a second)

        // when clicked it will call the beforeBedCashIn method
        beforeBedCashInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { beforeBedCashIn(); }
        });

        // when clicked it will call the OpenToDoListActivity method
        toDoListPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  { openToDoListActivity(); }
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

        loadData(); // calls loadData
    }

    /**
     * This method will be called when a user clicks the newDailyButton when clicked will take the
     * string inside the EditText box known as "taskEditText" and it will check if the string is
     * empty if all of the spaces are taken out. If the string is not empty then set the text box
     * to empty, create a new task with the string from the EditText box, create a new checkbox
     * state to correspond with it, set it to "Incomplete", and write the value of the checkbox state
     * and the new task to their appropriate files then show a toast saying "Task Added".
     * If the box is empty when trimmed then show a toast saying "Please enter a task".
     *
     * @param  v  The view of the button being clicked
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newDailyButton:
                String taskEntered = taskEditText.getText().toString();
                if (!taskEntered.trim().isEmpty()) { // if the task is not empty when it has spaces removed then...
                    adapter.add(taskEntered); //make it so they can't enter something with a length of less than 1 or something with no "characters"
                    taskEditText.setText("");
                    taskState.add("Incomplete");
                    FileHandler.writeDailiesData(tasks, this);
                    FileHandler.setCheckboxValue(taskState, this);

                    Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
                    break;
                }
                else{
                    Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show();
                }
        }
    }

    /**
     * This method will iterate through the statuses of the tasks that the user as set and check
     * which ones the user has completed and which ones they haven't. For each one that they have
     * completed counterCompleted will be incremented, for each one they haven't counterIncomplete
     * will be incremented. The total completed tasks will give the user 10 gold for each task they
     * have completed which gets added onto goldValue. The total incomplete tasks will remove 5
     * health for each task and overwrite healthValue. Both of the values update their associated
     * TextViews. All completed tasks statuses are set to incomplete the state of each task is then
     * saved to overwrite it's file. The user get's a toast to show that they have cashed in and
     * good night. The data is then saved again.
     */
    public void beforeBedCashIn() {
        int counterCompleted = 0;
        int counterIncomplete = 0;
        for (int i=0; i < taskState.size(); i++) {
            if (taskState.get(i).equals("Completed")) {
                taskState.set(i, "Incomplete");
                counterCompleted = counterCompleted + 1;
            }
            else {
                counterIncomplete = counterIncomplete + 1;
            }
        }
        healthValue = healthValue - (counterIncomplete*5);
        goldValue = goldValue + (counterCompleted*10);

        if (healthValue < 0) {
            healthValue = 0;
            Toast.makeText(this, "You are hurt badly and can't fight until you have healed!", Toast.LENGTH_SHORT).show();
        }

        healthTextView.setText(String.valueOf(healthValue));
        goldTextView.setText(String.valueOf(goldValue));

        FileHandler.setCheckboxValue(taskState, this);

        taskState = FileHandler.readCheckboxData(this);
        adapterSecondary = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskState);
        taskListState.setAdapter(adapterSecondary);
        Toast.makeText(this, "Innkeeper says: 'Your tasks have been cashed in. Good night'", Toast.LENGTH_LONG).show();

        saveData();
    }


    /**
     * When called this method will check if the corresponding position of the item clicked is
     * "Incomplete", if it is then it will change it to "Completed". Otherwise it will set it to
     * "Incomplete".
     * These values are then written to their appropriate files and update the current
     * checkboxState and the adapter that corresponds with it.
     *
     * @param  parent  The parent adapter view
     * @param  view  The current view
     * @param  position  The Integer position of the item clicked (the first being 0)
     * @param  id  The id given to the item clicked
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("Clicked view: "+ view);

        if (taskState.get(position).equals("Incomplete")) {
            Toast.makeText(this, "Task Completed", Toast.LENGTH_SHORT).show();
            System.out.println("Checked: "+ position);
            taskState.set(position, "Completed");
        }
        else {
            Toast.makeText(this, "Task undone", Toast.LENGTH_SHORT).show();
            System.out.println("Unchecked: "+ position);
            taskState.set(position, "Incomplete");
        }

        FileHandler.setCheckboxValue(taskState, this);
        System.out.println(taskState);
        taskState = FileHandler.readCheckboxData(this);
        adapterSecondary = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskState);
        taskListState.setAdapter(adapterSecondary);

    }

    /**
     * When called this method will remove the task and checkbox corresponding with the position
     * of the item clicked and held. The new tasks and checkBoxState ArrayList values are written
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
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        tasks.remove(position);
        adapter.notifyDataSetChanged();
        taskState.remove(position);

        FileHandler.writeDailiesData(tasks, this);
        FileHandler.setCheckboxValue(taskState, this);
        Toast.makeText(this, "Task Removed", Toast.LENGTH_SHORT).show();

        return true;
    }

    /**
     * This method will call saveData and call the startActivity method and pass the intent to it this will let it
     * know which page to go to, in this case that will be the ToDoList page
     */
    public void openToDoListActivity() {
        saveData();
        Intent intent = new Intent(this, ToDoListActivity.class);
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