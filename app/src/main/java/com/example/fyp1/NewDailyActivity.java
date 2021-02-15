package com.example.fyp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NewDailyActivity extends AppCompatActivity {

    //String title = "";
    //String description = "";

    EditText titleInput;
    EditText descriptionInput;

    Button submitButton;
    Button cancelNewDailyButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_daily);

        //titleInput = (EditText) findViewById(R.id.titleOfTaskBox);
        titleInput = findViewById(R.id.titleOfTaskBox);
        descriptionInput = (EditText) findViewById(R.id.descriptionOfTaskBox);

        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeDailyToFile();
                openDailyActivity();
                //title = titleInput.getText().toString();
                //description = descriptionInput.getText().toString();

                //Log.d("title is: ", title);
                //Log.d("description is: ", description);

                //put these somewhere and get them onto the orignal page as a check box - store in CSV file
            }
        });

        cancelNewDailyButton = (Button) findViewById(R.id.cancelNewDailyButton);
        cancelNewDailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                openDailyActivity();
            }
        });

    }

    public void openDailyActivity() {
        Intent intent = new Intent(this, DailyTasksActivity.class);
        startActivity(intent);
    }

    public void writeDailyToFile() {
        String textForTitle = titleInput.getText().toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput("DailyTasks.txt", MODE_PRIVATE);
            fileOutputStream.write(textForTitle.getBytes());
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}