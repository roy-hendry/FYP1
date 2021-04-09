package com.example.fyp1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ShopActivity extends AppCompatActivity {

    private ImageButton potionImageButton; // declaring private views (interfaces)
    private ImageButton daggerImageButton;

    private Button dailiesPageButton;
    private Button toDoListPageButton;
    private Button combatPageButton;

    private TextView healthTextView;
    private TextView goldTextView;
    private TextView combatTextView;

    private int healthValue;
    private int goldValue;
    private int combatValue;

    public static final String SHARED_PREFERENCES = "sharedPreferences"; // creating static values (values that can't be changed) so that the shared preference can use them
    public static final String HEALTH_VALUE = "healthValue";
    public static final String GOLD_VALUE = "goldValue";
    public static final String COMBAT_VALUE = "combatValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop); // link up the activity_shop.xml file to this file as the corresponding file

        dailiesPageButton = findViewById(R.id.dailiesPageButton); // attaching the xml interfaces to the interfaces we made above
        toDoListPageButton = findViewById(R.id.toDoListPageButton);
        combatPageButton = findViewById(R.id.combatPageButton);
        potionImageButton = findViewById(R.id.potionImageButton);
        daggerImageButton = findViewById(R.id.daggerImageButton);
        healthTextView = findViewById(R.id.healthValueTextView);
        goldTextView = findViewById(R.id.goldValueTextView);
        combatTextView = findViewById(R.id.combatValueTextView);

        healthTextView.setText(String.valueOf(healthValue)); // Setting the value of the healthTextView to be the same as the integer held in healthValue
        goldTextView.setText(String.valueOf(goldValue)); // Setting the value of the goldTextView to be the same as the integer held in goldValue
        combatTextView.setText(String.valueOf(combatValue)); // Setting the value of the combatTextView to be the same as the integer held in combatValue

        potionImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  { buyPotion(); }
        });

        daggerImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  { buyDagger(); }
        });

        // when clicked it will call the OpenDailiesActivity method
        dailiesPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  { openDailiesActivity(); }
        });

        // when clicked it will call the OpenToDoListActivity method
        toDoListPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  { openToDoListActivity(); }
        });

        // when clicked it will call the OpenCombatActivity method
        combatPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  { openCombatActivity(); }
        });

        loadData();
    }

    /**
     *
     */
    public void buyPotion() {
        if (goldValue >= 50) {
            if (healthValue == 100) {
                Toast.makeText(this, "Shop owner says: 'You seem healthy enough!'", Toast.LENGTH_LONG).show();
            }
            else {
                goldValue = goldValue - 50;
                healthValue = healthValue + 25;
                if (healthValue >= 100) {
                    healthValue = 100;
                }
                Toast.makeText(this, "You drink the simple health potion and gain some health", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Shop owner says: 'You don't have enough gold, come back after you've completed more tasks'", Toast.LENGTH_LONG).show();
        }
        healthTextView.setText(String.valueOf(healthValue));
        goldTextView.setText(String.valueOf(goldValue));

        saveData();
    }

    /**
     *
     */
    public void buyDagger() {
        if (goldValue >= 100) {
            if (combatValue == 5) {
                Toast.makeText(this, "Shop owner says: 'You already have one of these!'", Toast.LENGTH_LONG).show();
            }
            else {
                goldValue = goldValue - 100;
                combatValue = 5;
                Toast.makeText(this, "Shop owner says: 'Here you go!'", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Shop owner says: 'You don't have enough gold, come back after you've completed more tasks'", Toast.LENGTH_LONG).show();
        }
        goldTextView.setText(String.valueOf(goldValue));
        combatTextView.setText(String.valueOf(combatValue));

        saveData();
    }

    /**
     * This method will call saveData and call the startActivity method and pass the intent to it this will let it
     * know which page to go to, in this case that will be the DailyTasksActivity page
     */
    public void openDailiesActivity() {
        saveData();
        Intent intent = new Intent(this, DailyTasksActivity.class);
        startActivity(intent);
    }

    /**
     * This method will call saveData and call the startActivity method and pass the intent to it this will let it
     * know which page to go to, in this case that will be the ToDoList page
     */
    public void openToDoListActivity() {
        saveData();
        Intent intent = new Intent(this, ToDoListActivity.class);
        startActivity(intent);
    }

    /**
     * This method will call saveData and call the startActivity method and pass the intent to it this will let it
     * know which page to go to, in this case that will be the Combat page
     */
    public void openCombatActivity() {
        saveData();
        Intent intent = new Intent(this, CombatActivity.class);
        startActivity(intent);
    }

    /**
     * When called this method will create a new set of shared preferences that will enable
     * it to save values of the variables held in it and commit them to preferences
     */
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(HEALTH_VALUE, healthValue);
        editor.putInt(GOLD_VALUE, goldValue);
        editor.putInt(COMBAT_VALUE, combatValue);

        editor.commit();
    }

    /**
     * When called this method will load up the values previously set from the saveData method
     * if the variables have no previously stored preference then the defaults will be as shown
     * below
     */
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);

        healthValue = sharedPreferences.getInt(HEALTH_VALUE, 100);
        healthTextView.setText(String.valueOf(healthValue));

        goldValue = sharedPreferences.getInt(GOLD_VALUE, 0);
        goldTextView.setText(String.valueOf(goldValue));

        combatValue = sharedPreferences.getInt(COMBAT_VALUE, 1);
        combatTextView.setText(String.valueOf(combatValue));
    }
}