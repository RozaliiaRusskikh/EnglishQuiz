package com.e.englishquiz.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.e.englishquiz.Models.PhrasalVerb;

import java.util.ArrayList;

public class PhrasalVerbRepository extends RepositoryBase {

    SQLiteDatabase db = getWritableDatabase();

    public PhrasalVerbRepository(Context context) {
        super(context);
    }

    public ArrayList<PhrasalVerb> getAllVerbs() {

        ArrayList<PhrasalVerb> verbs = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM verbs ORDER BY title", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String example = cursor.getString(cursor.getColumnIndex("example"));
                Boolean known = (cursor.getInt(cursor.getColumnIndex("isKnown")) > 0);

                PhrasalVerb verb = new PhrasalVerb(id, title, description, example, known);

                verbs.add(verb);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return verbs;
    }

    public void updateIsKnownParameter(Boolean isKnown, PhrasalVerb selectedVerb) {
        ContentValues values = new ContentValues();
        values.put("isKnown", isKnown ? 1 : 0);

        db.update(
                "verbs",
                values,
                "_id=?",
                new String[]{Integer.toString(selectedVerb.getId())});
    }
}
