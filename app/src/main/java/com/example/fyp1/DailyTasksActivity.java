package com.example.fyp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class DailyTasksActivity extends AppCompatActivity {

    private Button newDailyButton;

    TextView dailiesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        dailiesText = findViewById(R.id.showDailiesText);

        newDailyButton = (Button) findViewById(R.id.newDailyButton);
        newDailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v)  {
                openNewDailyActivity();
            }
        });

        readDailiesFile();
    }
    public void openNewDailyActivity() {
            Intent intent = new Intent(this, NewDailyActivity.class);
            startActivity(intent);
    }

    public void readDailiesFile() {
        try {
            FileInputStream fileInputStream = openFileInput("DailyTasks.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            String dailyInputs;
            while ((dailyInputs = bufferedReader.readLine()) !=null) {
                stringBuffer.append(dailyInputs + "\n");
            }

            dailiesText.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}