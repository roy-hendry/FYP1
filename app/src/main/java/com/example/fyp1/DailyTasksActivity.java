package com.example.fyp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class DailyTasksActivity extends AppCompatActivity implements OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private Button newDailyButton;
    private EditText taskEditText;
    private ListView taskList;
    private Button toDoListPageButton;

    private ArrayList<String> tasks;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> checkboxState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        taskEditText = findViewById(R.id.taskEditText);
        newDailyButton = findViewById(R.id.newDailyButton);
        taskList = findViewById(R.id.taskList);

        tasks = FileHandler.readDailiesData(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, tasks); //check if I can make this into a checkbox list instead of just a normal list
        taskList.setAdapter(adapter);

        checkboxState = FileHandler.readCheckboxData(this);

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
                    FileHandler.writeDailiesData(tasks, this);
                    FileHandler.setUpCheckbox(checkboxState, this);
                    System.out.println(checkboxState);
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
        CheckedTextView v = (CheckedTextView) view;
        v.setChecked(!v.isChecked());
        adapter.notifyDataSetChanged();
        System.out.println(tasks);

        //Log.d("test",taskList.get(position).get("ID"));
        if (v.isChecked()) {
            Toast.makeText(this, "Task Completed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Task Unchecked", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        tasks.remove(position);

        adapter.notifyDataSetChanged();
        FileHandler.writeDailiesData(tasks, this);
        Toast.makeText(this, "Task Removed", Toast.LENGTH_SHORT).show();
        return true;
    }
}