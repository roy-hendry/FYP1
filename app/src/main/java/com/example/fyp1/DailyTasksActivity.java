package com.example.fyp1;

import androidx.appcompat.app.AppCompatActivity; // importing required packages
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class DailyTasksActivity extends AppCompatActivity implements OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private Button newDailyButton; // declaring private views (interfaces)
    private EditText taskEditText;
    private ListView taskList;
    private ListView taskListState;
    private Button toDoListPageButton;

    private ArrayList<String> tasks; // declaring a private ArrayLists to store all of the tasks the user has made
    private ArrayList<String> checkboxState; // declaring a private ArrayList to reflect the state of the tasks on the tasks ArrayList
    private ArrayAdapter<String> adapter; // declaring adapter ArrayList to interact with elements of the ListView
    private ArrayAdapter<String> adapterSecondary; // declaring adapter ArrayList to interact with elements of the task ListView's states

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily); // link up the activity_daily.xml file to this file as the corresponding file

        taskEditText = findViewById(R.id.taskEditText); // attaching the xml interfaces to the interfaces we made above
        newDailyButton = findViewById(R.id.newDailyButton);
        taskList = findViewById(R.id.taskList);
        taskListState = findViewById(R.id.taskListState);
        toDoListPageButton = findViewById(R.id.toDoListPageButton);

        tasks = FileHandler.readDailiesData(this); // calls the readDailiesData method inside the FileHandler to recall data written from the appropriate file and reload it
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks); // create a new ArrayAdapter that uses a simple list based off the tasks
        taskList.setAdapter(adapter); // setting the new ArrayAdapter to correspond with the ListView of tasks
        checkboxState = FileHandler.readCheckboxData(this); // by calling readCheckBoxData data will be read from inside the appropriate
                                                                    // file and loaded to set the checkbox states to what they were previously
        adapterSecondary = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, checkboxState); // the same as the lines above but for the status ListView
        taskListState.setAdapter(adapterSecondary);

        System.out.println("tasks: "+ tasks);
        System.out.println("checkboxState: "+ checkboxState);

        newDailyButton.setOnClickListener(this); // setting newDailyButton have onClick capabilities
        taskList.setOnItemClickListener(this); // onClick capabilities for the ListView
        taskList.setOnItemLongClickListener(this); // onLongClick capabilities for the ListView (click and hold for over a second)

        // setting toDoListButton to have onClick capabilities
        toDoListPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  { openToDoListActivity(); } // when clicked it will call the OpenToDoListActivity method
        });
    }

    /**
     * This method will call the startActivity method and pass the intent to it this will let it
     * know which page to go to, in this case that will be the ToDoList page
     */
    public void openToDoListActivity() {
        Intent intent = new Intent(this, ToDoListActivity.class);
        startActivity(intent);
    }

    /**
     * This method will be called when a user clicks the newDailyButton when clicked will take the
     * string inside the EditText box known as "taskEditText" and it will check if the string is
     * empty if all of the spaces are taken out.
     * If the string is not empty then set the text box
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
                    checkboxState.add("Incomplete");
                    FileHandler.writeDailiesData(tasks, this);
                    FileHandler.setCheckboxValue(checkboxState, this);

                    Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
                    break;
                }
                else{
                    Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show();
                }
        }
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

        if (checkboxState.get(position).equals("Incomplete")) {
            Toast.makeText(this, "Task Completed", Toast.LENGTH_SHORT).show();
            System.out.println("Checked: "+ position);
            checkboxState.set(position, "Completed");
        }
        else {
            Toast.makeText(this, "Task undone", Toast.LENGTH_SHORT).show();
            System.out.println("Unchecked: "+ position);
            checkboxState.set(position, "Incomplete");
        }

        FileHandler.setCheckboxValue(checkboxState, this);
        System.out.println(checkboxState);
        checkboxState = FileHandler.readCheckboxData(this);
        adapterSecondary = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, checkboxState);
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
        checkboxState.remove(position);

        FileHandler.writeDailiesData(tasks, this);
        FileHandler.setCheckboxValue(checkboxState, this);
        Toast.makeText(this, "Task Removed", Toast.LENGTH_SHORT).show();

        return true;
    }

}