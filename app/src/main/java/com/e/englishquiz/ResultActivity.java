package com.e.englishquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ResultActivity extends AppCompatActivity {

    private static final String EXTRA_RESULT_SHOWN = "com.e.englishquiz.result_shown"; // constant for the extra's key
    private String mPercentResult;
    private TextView mResultTextView;
    private ImageButton mGiftButton;

    public static Intent newIntent(Context packageContext, String percentResult) {
        Intent intent = new Intent(packageContext,ResultActivity.class);
        intent.putExtra(EXTRA_RESULT_SHOWN,percentResult);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mPercentResult = getIntent().getStringExtra(EXTRA_RESULT_SHOWN);

        mResultTextView = (TextView) findViewById(R.id.percent_of_correct_answers);
        mResultTextView.setText(mPercentResult + "%");

        mGiftButton = (ImageButton) findViewById(R.id.gift_button);
        mGiftButton.setOnClickListener(new View.OnClickListener() { // setting listeners
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, ActivityWishes.class);
                intent.putExtra("result", mPercentResult);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) { // saving data across rotation
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(EXTRA_RESULT_SHOWN, mPercentResult);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}