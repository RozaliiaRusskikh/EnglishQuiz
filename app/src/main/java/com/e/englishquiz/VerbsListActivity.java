package com.e.englishquiz;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.util.ArrayList;


public class VerbsListActivity extends AppCompatActivity {

    private QuestionsRepository questionsRepository;
    private SQLiteDatabase mDb;

    private ArrayList<PhrasalVerb> mVerbs;

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

        ItemAdapter adapter = new ItemAdapter(mVerbs, this);

        ListView lv = (ListView) findViewById(R.id.list_view);
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
