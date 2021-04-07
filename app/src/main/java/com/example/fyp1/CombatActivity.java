package com.example.fyp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CombatActivity extends AppCompatActivity {

    private Button dailiesPageButton;
    private Button toDoListPageButton;
    private Button shopPageButton;

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
        setContentView(R.layout.activity_combat);

        dailiesPageButton = findViewById(R.id.dailiesPageButton);
        toDoListPageButton = findViewById(R.id.toDoListPageButton);
        shopPageButton = findViewById(R.id.shopPageButton);
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

        // setting shopButton to have onClick capabilities
        shopPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  { openShopActivity(); } // when clicked it will call the OpenShopActivity method
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
     * know which page to go to, in this case that will be the Shop page
     */
    public void openShopActivity() {
        saveData();
        Intent intent = new Intent(this, ShopActivity.class);
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