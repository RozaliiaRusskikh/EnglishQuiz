package com.e.englishquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index"; // key for bundle

    // adding member variables and a Question array
    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;
    private Button mNextButton;

    private Question[] mQuestions = new Question[]{
            new Question(R.string.question_about_auxiliary_verb, false),
            new Question(R.string.question_about_must, false),
            new Question(R.string.question_about_phrasal_verb, false),
            new Question(R.string.question_about_shortest_sentence, false),
            new Question(R.string.question_about_used_to_do, false),
            new Question(R.string.question_about_verb_to_think, true),
            new Question(R.string.question_about_verb_to_do, false)
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        if (savedInstanceState != null) { // reading saving data in SaveInstantState back
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view); // getting references to widget

        mTrueButton = (Button) findViewById(R.id.true_button); // getting references to widget
        mTrueButton.setOnClickListener(new View.OnClickListener() { // setting listener
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                mTrueButton.setEnabled(false); // disabling the buttons to prevent multiple answers being entered
                mFalseButton.setEnabled(false);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button); // getting references to widget
        mFalseButton.setOnClickListener(new View.OnClickListener() {  // setting listener
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                mTrueButton.setEnabled(false); // disabling the buttons to prevent multiple answers being entered
                mFalseButton.setEnabled(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button); // getting references to widget
        mNextButton.setOnClickListener(new View.OnClickListener() {  // setting listener
            @Override
            public void onClick(View view) {
                mTrueButton.setEnabled(true);
                mFalseButton.setEnabled(true);
                mCurrentIndex++; // incrementing the index
                updateQuestion();
                int lastIndex = mQuestions.length - 1;
                if (mCurrentIndex == lastIndex) {
                    mNextButton.setEnabled(false);
                }
            }
        });
        updateQuestion();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) { // saving data across rotation
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() { // updating TextView's text
        int question = mQuestions[mCurrentIndex].getQuestionTextId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerTrue = mQuestions[mCurrentIndex].isAnswerTrue();
        int messageResultID = 0;
        if (userPressedTrue == answerTrue) {
            messageResultID = R.string.correct_toast;
        } else {
            messageResultID = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResultID, Toast.LENGTH_SHORT).show(); //making toast
    }
}
