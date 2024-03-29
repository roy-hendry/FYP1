package com.example.fyp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class CombatActivity extends AppCompatActivity {

    private Button fightButton; // declaring private views (interfaces)

    private Button dailiesPageButton;
    private Button toDoListPageButton;
    private Button shopPageButton;

    private TextView healthTextView;
    private TextView goldTextView;
    private TextView combatTextView;
    private TextView enemyHealthView;
    private int healthValue;
    private int goldValue;
    private int combatValue;

    private int enemyHealth = 100;

    public static final String SHARED_PREFERENCES = "sharedPreferences"; // creating static values (values that can't be changed) so that the shared preference can use them
    public static final String HEALTH_VALUE = "healthValue";
    public static final String GOLD_VALUE = "goldValue";
    public static final String COMBAT_VALUE = "combatValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat); // link up the activity_combat.xml file to this file as the corresponding file

        dailiesPageButton = findViewById(R.id.dailiesPageButton); // attaching the xml interfaces to the interfaces we made above
        toDoListPageButton = findViewById(R.id.toDoListPageButton);
        shopPageButton = findViewById(R.id.shopPageButton);
        fightButton = findViewById(R.id.fightButton);
        healthTextView = findViewById(R.id.healthValueTextView);
        goldTextView = findViewById(R.id.goldValueTextView);
        combatTextView = findViewById(R.id.combatValueTextView);
        enemyHealthView = findViewById(R.id.enemyHealthValueTextView);

        healthTextView.setText(String.valueOf(healthValue)); // Setting the text views so that they show item values
        goldTextView.setText(String.valueOf(goldValue));
        combatTextView.setText(String.valueOf(combatValue));
        enemyHealthView.setText(String.valueOf(enemyHealth));

        fightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  { fight(); }
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

        // when clicked it will call the OpenShopActivity method
        shopPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  { openShopActivity(); }
        });

        loadData();
    }

    /**
     * This method when called will check the user's health, if the user's health is 0 then they
     * will be told that they cannot fight in this state. Otherwise while the user and the enemy
     * both have more than 0 health, they will be have a random value calculated to be multiplied
     * by their combat value. The more the user's combat value the less damage that take per loop
     * and the more damage the enemy take's per loop.
     * Once the loop is exited there is another check to see if the user's health is below
     *  0. If it is then it will reset the user's health to 0 and show them a toast to say that
     * they have taken too much damage so cannot fight. Otherwise they are given gold partially
     * based off a randomly generated value. Then they are told that they have been given gold.
     * The enemy's health is reset to 100, all of the TextViews for the user values (health and gold)
     * are updated then the game's SharedPreferences are saved.
     */
    public void fight() {
        if (healthValue == 0) {
            Toast.makeText(this, "You can't fight like this, heal up first!", Toast.LENGTH_SHORT).show();
        }
        else {
            //Random random = new Random();
            while (enemyHealth > 0 & healthValue > 0) {
                Random random = new Random();
                int randDamageInt = random.nextInt(5) + 1; //choose a random number between 1 and 5 (5 is not included by default so we need to add 1)
                int damageCalc = combatValue * randDamageInt;
                int userDamage = (100 - damageCalc) / 10;
                healthValue = healthValue - userDamage;
                enemyHealth = enemyHealth - ((damageCalc * 2) + 5);
            }
            if (healthValue < 0) {
                healthValue = 0;
                Toast.makeText(this, "You've taken a lot of damage so you run away from the enemy!", Toast.LENGTH_LONG).show();
                Toast.makeText(this, "Heal up and come back!", Toast.LENGTH_SHORT).show();
            }
            else {
                Random random = new Random();
                int randRewardInt = random.nextInt(10) + 1;
                goldValue = goldValue + 10 * randRewardInt;
                Toast.makeText(this, "Good job, you defeated the enemy and have been rewarded gold!", Toast.LENGTH_LONG).show();
            }
        }
        enemyHealth = 100;
        healthTextView.setText(String.valueOf(healthValue)); // updating views showing player values
        goldTextView.setText(String.valueOf(goldValue));
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
     * know which page to go to, in this case that will be the Shop page
     */
    public void openShopActivity() {
        saveData();
        Intent intent = new Intent(this, ShopActivity.class);
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