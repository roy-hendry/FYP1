package com.example.fyp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DailyTasksActivity extends AppCompatActivity implements OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private Button newDailyButton;
    private EditText taskEditText;
    private ListView taskList;
    private ListView taskListState;
    private Button toDoListPageButton;

    private ArrayList<String> tasks;
    private ArrayList<String> checkboxState;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapterSecondary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        taskEditText = findViewById(R.id.taskEditText);
        newDailyButton = findViewById(R.id.newDailyButton);
        taskList = findViewById(R.id.taskList);
        taskListState = findViewById(R.id.taskListState);

        tasks = FileHandler.readDailiesData(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        taskList.setAdapter(adapter);
        checkboxState = FileHandler.readCheckboxData(this);
        adapterSecondary = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, checkboxState);
        taskListState.setAdapter(adapterSecondary);

        System.out.println("tasks: "+ tasks);
        System.out.println("checkboxState: "+ checkboxState);

        newDailyButton.setOnClickListener(this);
        taskList.setOnItemClickListener(this);
        taskList.setOnItemLongClickListener(this);

        toDoListPageButton = findViewById(R.id.toDoListPageButton);
        toDoListPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  { openToDoListActivity(); }
        });
    }

    public void openToDoListActivity() {
        Intent intent = new Intent(this, ToDoListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newDailyButton:
                String taskEntered = taskEditText.getText().toString();
                if (!taskEntered.trim().isEmpty()) {
                    adapter.add(taskEntered); //make it so they can't enter something with a length of less than 1 or something with no "characters"
                    taskEditText.setText("");
                    checkboxState.add("false");
                    FileHandler.writeDailiesData(tasks, this);
                    FileHandler.setUpCheckbox(checkboxState, this);

                    Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
                    break;
                }
                else{
                    Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("Clicked view: "+ view);

        if (checkboxState.get(position).equals("false")) {
            Toast.makeText(this, "Task Completed", Toast.LENGTH_SHORT).show();
            System.out.println("Checked: "+ position);
            checkboxState.set(position, "true");
        }
        else {
            Toast.makeText(this, "Task Unchecked", Toast.LENGTH_SHORT).show();
            System.out.println("Unchecked: "+ position);
            checkboxState.set(position, "false");
        }

        FileHandler.setCheckboxValue(checkboxState, this);
        System.out.println(checkboxState);
        checkboxState = FileHandler.readCheckboxData(this);
        adapterSecondary = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, checkboxState);
        taskListState.setAdapter(adapterSecondary);

    }

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