package com.e.englishquiz;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class PhrasalVerbActivity extends AppCompatActivity {

    private TextView mTitle;
    private TextView mMeaning;
    private TextView mExample;
    private CheckBox mKnow;

    private static final String EXTRA_VERB_TITLE = "com.e.englishquiz.verb_title"; // constant for the extra's key
    private static final String EXTRA_VERB_MEANING = "com.e.englishquiz.verb_meaning"; // constant for the extra's key
    private static final String EXTRA_VERB_EXAMPLE = "com.e.englishquiz.verb_example"; // constant for the extra's key
    private static final String EXTRA_KNOWN = "com.e.englishquiz.verb_known"; // constant for the extra's key

    public static Intent newIntent(Context packageContext, String title, String meaning, String example, Boolean isKnown) {
        Intent intent = new Intent(packageContext, PhrasalVerbActivity.class);
        intent.putExtra(EXTRA_VERB_TITLE, title);
        intent.putExtra(EXTRA_VERB_MEANING, meaning);
        intent.putExtra(EXTRA_VERB_EXAMPLE, example);
        intent.putExtra(EXTRA_KNOWN, isKnown);
        return intent;
    }

    public static boolean wasKnown(Intent result) { // Decoding the result intent
        return result.getBooleanExtra(EXTRA_KNOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrasal_verb);

        String verbTitle = getIntent().getStringExtra(EXTRA_VERB_TITLE);
        String verbMeaning = getIntent().getStringExtra(EXTRA_VERB_MEANING);
        String verbExample = getIntent().getStringExtra(EXTRA_VERB_EXAMPLE);
        Boolean verbIsKnown = getIntent().getBooleanExtra(EXTRA_KNOWN, false);

        mTitle = (TextView) findViewById(R.id.verb);
        mTitle.setText(verbTitle);

        mMeaning = (TextView) findViewById(R.id.meaning);
        mMeaning.setText(verbMeaning);

        mExample = (TextView) findViewById(R.id.example);
        mExample.setText(verbExample);

        mKnow = (CheckBox) findViewById(R.id.verb_known);
        mKnow.setChecked(verbIsKnown);
        mKnow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setKnow(isChecked);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setKnow(boolean isKnown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_KNOWN, isKnown);
        setResult(RESULT_OK, data);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

