package com.e.englishquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Button mLearnButton; // wiring up widgets
    private Button mTrainButton;
    private Button mAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);

        mLearnButton = findViewById(R.id.learn_btn);
        mLearnButton.setOnClickListener(new View.OnClickListener() { // setting listeners
            @Override
            public void onClick(View v) {

            }
        });

        mTrainButton = findViewById(R.id.train_btn);
        mTrainButton.setOnClickListener(new View.OnClickListener() { // setting listeners
            @Override
            public void onClick(View v) {
                Intent intentTrain = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intentTrain);
            }
        });

        mAbout = (Button) findViewById(R.id.about_btn);
        mAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAbout = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intentAbout);
            }
        });
    }
}
