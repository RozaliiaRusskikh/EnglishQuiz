package com.e.englishquiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index"; // key for bundle
    private static final int REQUEST_CODE_CHEAT = 0; // request code (user-defined integer) that is sent to the child activity and then received back by the parent
    private static final String CHEAT = "cheat"; // request code (user-defined integer) that is sent to the child activity and then received back by the parent

    // adding member variables and a Question array
    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;
    private Button mNextButton;
    private Button mCheatButton;

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
    private int mCorrectAnswerAmount = 0;
    private boolean mIsCheater; // adding a member variable to hold the value that CheatActivity is passing back

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        if (savedInstanceState != null) { // reading saving data in SaveInstantState back
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(CHEAT, false);
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
                mIsCheater = false;
                updateQuestion();
                int lastIndex = mQuestions.length - 1;
                if (mCurrentIndex == lastIndex) { // disabling the Next button when there is no next question
                    mNextButton.setEnabled(false);
                }
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestions[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT); //starting CheatActivity and hearing back from the child activity
            }
        });
        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // retrieving value that CheatActivity passing back
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
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
        savedInstanceState.putBoolean(CHEAT, mIsCheater);
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
        String messageResult = null;
        if (mIsCheater) {
            messageResult = this.getResources().getString(R.string.juidgment_toast);
        } else {
            if (userPressedTrue == answerTrue) {
                messageResult = this.getResources().getString(R.string.correct_toast);
                mCorrectAnswerAmount++;
            } else {
                messageResult = this.getResources().getString(R.string.incorrect_toast);
            }
        }

        int questionsAmount = mQuestions.length;
        int lastIndex = questionsAmount - 1;
        if (mCurrentIndex == lastIndex) {
            double percentageScore = (mCorrectAnswerAmount / (double) questionsAmount) * 100;
            String percentageScoreString = new DecimalFormat("#0.00").format(percentageScore);

            messageResult = messageResult + "\n" + "Percentage of correct answers is " + percentageScoreString; // displaying text for Toast with a percentage correct score
        }

        Toast.makeText(this, messageResult, Toast.LENGTH_SHORT).show(); //making toast
    }
}
