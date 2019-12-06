package com.e.englishquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ResultActivity extends AppCompatActivity {

    private static final String EXTRA_RESULT_SHOWN = "com.e.englishquiz.result_shown"; // constant for the extra's key
    private String mPercentResult;
    private TextView mResultTextView;

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
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}