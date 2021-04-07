package com.example.fyp1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShopActivity extends AppCompatActivity {

    private Button dailiesPageButton;
    private Button toDoListPageButton;
    private Button combatPageButton;

    private TextView healthTextView;
    private TextView goldTextView;

    private int healthValue;
    private int goldValue;

    public static final String SHARED_PREFERENCES = "sharedPreferences";
    public static final String HEALTH_VALUE = "healthValue";
    public static final String GOLD_VALUE = "goldValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        dailiesPageButton = findViewById(R.id.dailiesPageButton);
        toDoListPageButton = findViewById(R.id.toDoListPageButton);
        combatPageButton = findViewById(R.id.combatPageButton);
        healthTextView = findViewById(R.id.healthValueTextView);
        goldTextView = findViewById(R.id.goldValueTextView);

        healthTextView.setText(String.valueOf(healthValue));
        goldTextView.setText(String.valueOf(goldValue));

        dailiesPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  { openDailiesActivity(); } // when clicked it will call the OpenDailiesActivity method
        });

        // setting toDoListButton to have onClick capabilities
        toDoListPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  { openToDoListActivity(); } // when clicked it will call the OpenToDoListActivity method
        });

        // setting combatButton to have onClick capabilities
        combatPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  { openCombatActivity(); } // when clicked it will call the OpenCombatActivity method
        });

        loadData();
    }

    /**
     * This method will call the startActivity method and pass the intent to it this will let it
     * know which page to go to, in this case that will be the DailyTasksActivity page
     */
    public void openDailiesActivity() {
        saveData();
        Intent intent = new Intent(this, DailyTasksActivity.class);
        startActivity(intent);
    }

    /**
     * This method will call the startActivity method and pass the intent to it this will let it
     * know which page to go to, in this case that will be the ToDoList page
     */
    public void openToDoListActivity() {
        saveData();
        Intent intent = new Intent(this, ToDoListActivity.class);
        startActivity(intent);
    }

    /**
     * This method will call the startActivity method and pass the intent to it this will let it
     * know which page to go to, in this case that will be the Combat page
     */
    public void openCombatActivity() {
        saveData();
        Intent intent = new Intent(this, CombatActivity.class);
        startActivity(intent);
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(HEALTH_VALUE, healthValue);
        editor.putInt(GOLD_VALUE, goldValue);

        editor.commit();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);

        healthValue = sharedPreferences.getInt(HEALTH_VALUE, 100);
        healthTextView.setText(String.valueOf(healthValue));

        goldValue = sharedPreferences.getInt(GOLD_VALUE, 0);
        goldTextView.setText(String.valueOf(goldValue));
    }
}