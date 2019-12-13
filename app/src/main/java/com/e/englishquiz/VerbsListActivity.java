package com.e.englishquiz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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
    private PhrasalVerb mVerb;
    private boolean mIsKnown;

    private ImageView mChecked;

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

                mVerb = new PhrasalVerb(id, title, description, example, known);

                mVerbs.add(mVerb);

            } while (cursor.moveToNext());
        }

        cursor.close();

        ItemAdapter adapter = new ItemAdapter(mVerbs, this);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // getting the list and the detail parts working together. When a
            //user presses an item in the list of verbs, a new PhrasalVerbActivity will appear
            //and display the details for that instance of PhrasalVerb
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                PhrasalVerb selectedVerb = (PhrasalVerb) parent.getItemAtPosition(position);
                Intent intent = PhrasalVerbActivity.newIntent(VerbsListActivity.this, selectedVerb.getVerb(), selectedVerb.getMeaning(), selectedVerb.getExample());
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
            mIsKnown = PhrasalVerbActivity.wasKnown(data);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
