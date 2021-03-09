package com.example.fyp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class ToDoListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private Button newToDoTaskButton;
    private EditText toDoListEditText;
    private ListView toDoList;
    private Button dailiesPageButton;

    private ArrayList<String> tasks;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        toDoListEditText = findViewById(R.id.toDoListEditText);
        newToDoTaskButton = findViewById(R.id.newToDoTaskButton);
        toDoList = findViewById(R.id.toDoList);

        tasks = FileHandler.readToDoData(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks); //check if I can make this into a checkbox list instead of just a normal list
        toDoList.setAdapter(adapter);

        newToDoTaskButton.setOnClickListener(this);
        toDoList.setOnItemClickListener(this);
        toDoList.setOnItemLongClickListener(this);

        dailiesPageButton = findViewById(R.id.dailiesPageButton);
        dailiesPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  { openDailiesActivity(); }
        });
    }

    public void openDailiesActivity() {
        Intent intent = new Intent(this, DailyTasksActivity.class);
        startActivity(intent);
    }

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        tasks.remove(position);

        adapter.notifyDataSetChanged();
        FileHandler.writeToDoData(tasks, this);
        Toast.makeText(this, "Task Completed", Toast.LENGTH_SHORT).show();
    }

    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        tasks.remove(position);

        adapter.notifyDataSetChanged();
        FileHandler.writeToDoData(tasks, this);
        Toast.makeText(this, "Task Removed", Toast.LENGTH_SHORT).show();
        return true;
    }

}
