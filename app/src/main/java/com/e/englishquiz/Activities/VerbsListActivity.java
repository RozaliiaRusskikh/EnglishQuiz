package com.e.englishquiz.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e.englishquiz.Adapters.ItemAdapter;
import com.e.englishquiz.Models.PhrasalVerb;
import com.e.englishquiz.R;
import com.e.englishquiz.Repositories.PhrasalVerbRepository;

import java.util.ArrayList;


public class VerbsListActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_KNOWN = 0;

    private ArrayList<PhrasalVerb> mVerbs;
    private PhrasalVerb mSelectedVerb;
    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbs_list);

        Toolbar toolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mVerbs = new PhrasalVerbRepository(this).getAllVerbs();

        mAdapter = new ItemAdapter(mVerbs, this);

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // getting the list and the detail parts working together. When a
            //user presses an item in the list of verbs, a new PhrasalVerbActivity will appear
            //and display the details for that instance of PhrasalVerb
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                mSelectedVerb = (PhrasalVerb) parent.getItemAtPosition(position);
                Intent intent = PhrasalVerbActivity.newIntent(VerbsListActivity.this, mSelectedVerb.getId(), mSelectedVerb.getVerb(), mSelectedVerb.getMeaning(), mSelectedVerb.getExample(), mSelectedVerb.isKnown());
                startActivityForResult(intent, REQUEST_CODE_KNOWN); // Getting a result back from a child activity
            }
        });
    }

    // Handling a result from PhrasalVerbActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_KNOWN) {

            ArrayList<PhrasalVerb> newVerbs = new PhrasalVerbRepository(this).getAllVerbs();
            mVerbs.clear();
            mVerbs.addAll(newVerbs);

            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
