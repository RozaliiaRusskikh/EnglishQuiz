package com.e.englishquiz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PhrasalVerbActivity extends AppCompatActivity {

    private PhrasalVerb mPhrasaVerb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrasal_verb);

        mPhrasaVerb = new PhrasalVerb();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

