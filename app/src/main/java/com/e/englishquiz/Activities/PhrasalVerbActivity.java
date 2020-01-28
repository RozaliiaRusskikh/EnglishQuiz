package com.e.englishquiz.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
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
    int mCurrentIndex;
    PhrasalVerbRepository mVerbRepository;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrasal_verb);

        String verbTitle = getIntent().getStringExtra(EXTRA_VERB_TITLE);
        String verbMeaning = getIntent().getStringExtra(EXTRA_VERB_MEANING);
        String verbExample = getIntent().getStringExtra(EXTRA_VERB_EXAMPLE);
        Boolean verbIsKnown = getIntent().getBooleanExtra(EXTRA_KNOWN, false);
        mVerbId = getIntent().getIntExtra(EXTRA_ID, 0);

        mVerbRepository = new PhrasalVerbRepository(this);
        mVerbs = mVerbRepository.getAllVerbs();

        mCurrentIndex = getCurrentIndex();

        mTitle = findViewById(R.id.verb);
        mTitle.setText(verbTitle);

        mMeaning = findViewById(R.id.meaning);
        mMeaning.setText(verbMeaning);

        mExample = findViewById(R.id.example);
        mExample.setText(verbExample);

        mKnow = findViewById(R.id.verb_known);
        mKnow.setChecked(verbIsKnown);

        mKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean checked = ((CheckBox)v).isChecked();

                PhrasalVerb verb = findSelectedVerb();
                verb.setKnown(checked);

                mVerbRepository.updateIsKnownParameter(checked, verb);
            }
        });

        ImageButton mPreviousButton = findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previousIndex = mCurrentIndex - 1;
                if (previousIndex >= 0) {
                    updateVerb(previousIndex);
                    mCurrentIndex = previousIndex;
                }

                if (previousIndex < 0) {
                    previousIndex += mVerbs.size();
                    updateVerb(previousIndex);
                    mCurrentIndex = previousIndex;
                }
            }
        });

        ImageButton mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextIndex = mCurrentIndex + 1;
                if (nextIndex < mVerbs.size()) {
                    updateVerb(nextIndex);
                    mCurrentIndex = nextIndex;
                }
                if (nextIndex == mVerbs.size()) {
                    nextIndex -= mVerbs.size();
                    updateVerb(nextIndex);
                    mCurrentIndex = nextIndex;
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar_actionbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().
        setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().
        setDisplayShowHomeEnabled(true);
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
        PhrasalVerb verb = mVerbs.get(index);

        mVerbId = verb.getId();

        mTitle.setText(verb.getVerb());
        mMeaning.setText(verb.getMeaning());
        mExample.setText(verb.getExample());
        mKnow.setChecked(verb.isKnown());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}