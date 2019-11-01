package com.e.englishquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class QuizActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "index"; // key for bundle
    private static final String AMOUNT_CHEATS = "amount"; // key for bundle

    private static final int REQUEST_CODE_CHEAT = 0; // request code (user-defined integer) that is sent to the child activity and then received back by the parent
    private static final String CHEAT = "cheat"; // request code (user-defined integer) that is sent to the child activity and then received back by the parent

    // adding member variables and a Question array
    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mCheatLeftTimesTextView;

    // for database
    private QuestionsRepository repository; //instance of QuestionsRepository
    private SQLiteDatabase mDb;
    private ArrayList<Question> mQuestions;

    private int mCurrentIndex = 0;
    private int mCorrectAnswerAmount = 0;
    private boolean mIsCheater; // adding a member variable to hold the value that CheatActivity is passing back
    private int mCheatLeftTimes = 3; // allow the user to cheat a maximum of three times

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        repository = new QuestionsRepository(this);
        try {
            repository.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = repository.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        mQuestions = repository.getAll();


        if (savedInstanceState != null) { // reading saving data in SaveInstantState back
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0); // saving data
            mCheatLeftTimes = savedInstanceState.getInt(AMOUNT_CHEATS, 0); // saving data
            mIsCheater = savedInstanceState.getBoolean(CHEAT, false);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view); // getting references to widget
        String question = (mQuestions.get(mCurrentIndex).getQuestionText());
        mQuestionTextView.setText((question));

        mCheatLeftTimesTextView = (TextView) findViewById(R.id.number_of_remaining_cheat_token);

        mTrueButton = (Button) findViewById(R.id.true_button); // getting references to widget
        mTrueButton.setOnClickListener(new View.OnClickListener() { // setting listener
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                mTrueButton.setEnabled(false); // disabling the buttons to prevent multiple answers being entered
                mFalseButton.setEnabled(false);
                mCheatButton.setEnabled(false);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button); // getting references to widget
        mFalseButton.setOnClickListener(new View.OnClickListener() {  // setting listener
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                mTrueButton.setEnabled(false); // disabling the buttons to prevent multiple answers being entered
                mFalseButton.setEnabled(false);
                mCheatButton.setEnabled(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button); // getting references to widget
        mNextButton.setOnClickListener(new View.OnClickListener() {  // setting listener
            @Override
            public void onClick(View view) {
                mTrueButton.setEnabled(true);
                mFalseButton.setEnabled(true);
                if (mCheatLeftTimes > 0) {
                    mCheatButton.setEnabled(true);
                }
                mCurrentIndex++; // incrementing the index
                mIsCheater = false;
                updateQuestion();
                int lastIndex = mQuestions.size() - 1;
                if (mCurrentIndex == lastIndex) { // disabling the Next button when there is no next question
                    mNextButton.setEnabled(false);
                }
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheatLeftTimes > 0) { // checking  of cheat tokens amount
                    boolean answerIsTrue = mQuestions.get(mCurrentIndex).isAnswerTrue();
                    Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                    startActivityForResult(intent, REQUEST_CODE_CHEAT); //starting CheatActivity and hearing back from the child activity
                }
            }
        });

        mCheatLeftTimesTextView.setText(getString(R.string.cheat_left_times, mCheatLeftTimes)); // restore the message
        if (mCheatLeftTimes == 0) { // if no tokens remain, the Cheat button become disable
            mCheatButton.setEnabled(false);
        }

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
            // mCheatLeftTimes will only be changed when the answer has been shown. If the mCheatButton is clicked but the answer is not shown, the mCheatLeftTimes will not be changed.
            mCheatLeftTimes--;
            mCheatLeftTimesTextView.setText(getString(R.string.cheat_left_times, mCheatLeftTimes));//displaying the number of remaining cheat tokens

            if (mCheatLeftTimes == 0) { // if no tokens remain, the Cheat button become disable
                mCheatButton.setEnabled(false);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) { // saving data across rotation
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(AMOUNT_CHEATS, mCheatLeftTimes);
        savedInstanceState.putBoolean(CHEAT, mIsCheater);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void updateQuestion() { // updating TextView's text
        String question = mQuestions.get(mCurrentIndex).getQuestionText();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerTrue = mQuestions.get(mCurrentIndex).isAnswerTrue();
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

        int questionsAmount = mQuestions.size();
        int lastIndex = questionsAmount - 1;
        if (mCurrentIndex == lastIndex) {
            double percentageScore = (mCorrectAnswerAmount / (double) questionsAmount) * 100;
            String percentageScoreString = new DecimalFormat("#0.00").format(percentageScore);

            messageResult = messageResult + "\n" + "Percentage of correct answers is " + percentageScoreString; // displaying text for Toast with a percentage correct score
        }

        Toast.makeText(this, messageResult, Toast.LENGTH_SHORT).show(); //making toast
    }
}