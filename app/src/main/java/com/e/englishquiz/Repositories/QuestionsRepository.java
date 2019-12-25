package com.e.englishquiz.Repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.e.englishquiz.Models.Question;

import java.util.ArrayList;


public class QuestionsRepository extends RepositoryBase {

    public QuestionsRepository(Context context) {
        super(context);
    }

    public ArrayList<Question> getAllQuestions() {
        SQLiteDatabase db = getWritableDatabase();

        ArrayList<Question> questions = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM questions", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String questionText = cursor.getString(cursor.getColumnIndex("questionText"));
                Boolean answer = (cursor.getInt(cursor.getColumnIndex("answer")) > 0);

                Question question = new Question(id, questionText, answer);

                questions.add(question);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return questions;
    }
}