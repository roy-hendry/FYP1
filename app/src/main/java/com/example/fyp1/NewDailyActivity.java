package com.example.fyp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewDailyActivity extends AppCompatActivity {

    String title, description;

    private EditText titleInput;
    private EditText descriptionInput;

    private Button submitButton;
    private Button cancelNewDailyButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_daily);

        titleInput = (EditText) findViewById(R.id.titleOfTaskBox);
        descriptionInput = (EditText) findViewById(R.id.descriptionOfTaskBox);

        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleInput.getText().toString();
                description = descriptionInput.getText().toString();

                //Log.d("title is: ", title);
                //Log.d("description is: ", description);

                //put these somewhere and get them onto the orignal page as a check box
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
}