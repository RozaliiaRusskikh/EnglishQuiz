package com.e.englishquiz.Activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e.englishquiz.Models.PhrasalVerb;
import com.e.englishquiz.R;
import com.e.englishquiz.Repositories.PhrasalVerbRepository;

import java.util.ArrayList;


public class PhrasalVerbActivity extends AppCompatActivity {

    private TextView mTitle;
    private TextView mMeaning;
    private TextView mExample;
    private CheckBox mKnow;
    private ArrayList<PhrasalVerb> mVerbs;
    private int mVerbId;
    int currentIndex;


    private static final String EXTRA_VERB_TITLE = "com.e.englishquiz.verb_title"; // constant for the extra's key
    private static final String EXTRA_VERB_MEANING = "com.e.englishquiz.verb_meaning"; // constant for the extra's key
    private static final String EXTRA_VERB_EXAMPLE = "com.e.englishquiz.verb_example"; // constant for the extra's key
    private static final String EXTRA_KNOWN = "com.e.englishquiz.verb_known"; // constant for the extra's key
    private static final String EXTRA_ID = "com.e.englishquiz.verb_id"; // constant for the extra's key

    public static Intent newIntent(Context packageContext, int id, String title, String meaning, String example, Boolean isKnown) {
        Intent intent = new Intent(packageContext, PhrasalVerbActivity.class);
        intent.putExtra(EXTRA_ID, id);
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
        mVerbId = getIntent().getIntExtra(EXTRA_ID, 0);

        PhrasalVerbRepository verbRepository = new PhrasalVerbRepository(this);
        mVerbs = verbRepository.getAllVerbs();
        currentIndex = getCurrentIndex();

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

        ImageButton mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previousIndex = currentIndex - 1;
                if (previousIndex >= 0) {
                    updateVerb(previousIndex);
                    currentIndex = previousIndex;
                }

                if (previousIndex < 0) {
                    previousIndex += mVerbs.size();
                    updateVerb(previousIndex);
                    currentIndex = previousIndex;
                }
            }
        });

        ImageButton mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextIndex = currentIndex + 1;
                if (nextIndex < mVerbs.size()) {
                    updateVerb(nextIndex);
                    currentIndex = nextIndex;
                }
                if (nextIndex == mVerbs.size()) {
                    nextIndex -= mVerbs.size();
                    updateVerb(nextIndex);
                    currentIndex = nextIndex;
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().

                setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().

                setDisplayShowHomeEnabled(true);
    }

    private void setKnow(boolean isKnown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_KNOWN, isKnown);
        setResult(RESULT_OK, data);
    }

    private PhrasalVerb findSelectedVerb() {
        for (PhrasalVerb verb : mVerbs) {
            if (verb.getId() == mVerbId) {
                return verb;
            }
        }
        return null;
    }

    private int getCurrentIndex() {
        PhrasalVerb selectedVerb = findSelectedVerb();
        int currentIndex = mVerbs.indexOf(selectedVerb);
        return currentIndex;
    }


    private void updateVerb(int index) {
        String title = mVerbs.get(index).getVerb();
        mTitle.setText(title);
        String meaning = mVerbs.get(index).getMeaning();
        mMeaning.setText(meaning);
        String example = mVerbs.get(index).getExample();
        mExample.setText(example);
        Boolean known = mVerbs.get(index).isKnown();
        mKnow.setChecked(known);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

