package com.e.englishquiz;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.util.ArrayList;


public class VerbsListActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_KNOWN = 0;

    private QuestionsRepository questionsRepository;
    private SQLiteDatabase mDb;

    private ArrayList<PhrasalVerb> mVerbs;
    private PhrasalVerb mSelectedVerb;
    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbs_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        questionsRepository = new QuestionsRepository(this);

        try {
            questionsRepository.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = questionsRepository.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }


        mVerbs = new ArrayList<PhrasalVerb>();

        Cursor cursor = mDb.rawQuery("SELECT * FROM verbs ORDER BY title", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String example = cursor.getString(cursor.getColumnIndex("example"));
                Boolean known = (cursor.getInt(cursor.getColumnIndex("isKnown")) > 0);

                PhrasalVerb verb = new PhrasalVerb(id, title, description, example, known);

                mVerbs.add(verb);

            } while (cursor.moveToNext());
        }

        cursor.close();

        mAdapter = new ItemAdapter(mVerbs, this);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // getting the list and the detail parts working together. When a
            //user presses an item in the list of verbs, a new PhrasalVerbActivity will appear
            //and display the details for that instance of PhrasalVerb
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                mSelectedVerb = (PhrasalVerb) parent.getItemAtPosition(position);
                Intent intent = PhrasalVerbActivity.newIntent(VerbsListActivity.this, mSelectedVerb.getVerb(), mSelectedVerb.getMeaning(), mSelectedVerb.getExample(), mSelectedVerb.isKnown());
                startActivityForResult(intent, REQUEST_CODE_KNOWN); // Getting a result back from a child activity
            }
        });
    }

    // Handling a result from PhrasalVerbActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_KNOWN) {
            if (data == null) {
                return;
            }

            boolean isKnown = PhrasalVerbActivity.wasKnown(data);
            mSelectedVerb.setKnown(isKnown);
            mAdapter.notifyDataSetChanged();

            ContentValues values = new ContentValues();
            values.put("isKnown", isKnown ? 1 : 0);

            mDb.update(
                    "verbs",
                    values,
                    "_id=?",
                    new String[] { Integer.toString(mSelectedVerb.getId()) });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
