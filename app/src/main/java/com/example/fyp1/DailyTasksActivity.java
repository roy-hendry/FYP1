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
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;

public class DailyTasksActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button newDailyButton;
    private TextView dailiesText;
    private EditText taskEditText;
    private ListView taskList;

    private ArrayList<String> tasks;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        taskEditText = findViewById(R.id.taskEditText);
        newDailyButton = findViewById(R.id.newDailyButton);
        taskList = findViewById(R.id.taskList);

        tasks = FileHandler.readData(this);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks); //check if I can make this into a checkbox list instead of just a normal list
        taskList.setAdapter(adapter);

        newDailyButton.setOnClickListener(this);
        taskList.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newDailyButton:
                String taskEntered = taskEditText.getText().toString();
                adapter.add(taskEntered); //make it so they can't enter something with a length of less than 1 or something with no "characters"
                taskEditText.setText("");
                FileHandler.writeData(tasks, this);
                Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        tasks.remove(position);
        adapter.notifyDataSetChanged();
        FileHandler.writeData(tasks, this);
        Toast.makeText(this, "Task Completed", Toast.LENGTH_SHORT).show();
    }
}