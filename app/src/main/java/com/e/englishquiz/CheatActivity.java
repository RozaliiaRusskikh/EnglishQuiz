package com.e.englishquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.e.englishquiz.answer_is_true"; //adding an extra constant
    private static final String EXTRA_ANSWER_SHOWN = "com.e.englishquiz.answer_shown"; // constant for the extra's key
    private static final String ANSWER = "cheated_answer";
    private static final String CHEAT = "cheat";

    private Button mShowAnswerButton;
    private TextView mAnswerTextView;
    private boolean mAnswerIsTrue;
    private boolean mHasCheat;


    public static Intent newIntent(Context packageContext, boolean isAnswerTrue) { //creating method to encapsulating of implementation details (creating an Intent with the extras)
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, isAnswerTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) { // decoding the extra
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);//retrieving the value from the extra

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //enable cheating: show answer
                String answer = String.valueOf(mAnswerIsTrue);
                mAnswerTextView.setText(answer);
                setAnswerShownResult(true);
                mHasCheat = true;
            }
        });
    }

    private void setAnswerShownResult(boolean isAnswerShown) { // sending back an intent
        Intent data = new Intent(); // creating an intent instance
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown); // putting an extra on intent
        setResult(RESULT_OK, data); // setting a result (sending data back to the parent)
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putBoolean(ANSWER, mAnswerIsTrue);
        saveInstanceState.putBoolean(CHEAT, mHasCheat);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mAnswerIsTrue = savedInstanceState.getBoolean(ANSWER);
        mHasCheat = savedInstanceState.getBoolean(CHEAT);
        if (mHasCheat) {
            setAnswerShownResult(true);
            if (mAnswerIsTrue) {
                mAnswerTextView.setText(R.string.true_button);
            } else {
                mAnswerTextView.setText(R.string.false_button);
            }
        }
    }
}
