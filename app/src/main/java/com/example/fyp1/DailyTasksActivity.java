package com.example.fyp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DailyTasksActivity extends AppCompatActivity {

    private Button newDailyButton;
    public String dailyTitlesArray[];
    public String dailyDescriptionArray[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        newDailyButton = (Button) findViewById(R.id.newDailyButton);
        newDailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v)  {
                openNewDailyActivity();
            }
        });
    }
    public void openNewDailyActivity() {
            Intent intent = new Intent(this, NewDailyActivity.class);
            startActivity(intent);
    }
}