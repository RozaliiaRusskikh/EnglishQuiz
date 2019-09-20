package com.e.englishquiz;

public class Question {
    private int mQuestionTextId;
    private boolean mIsAnswerTrue;

    public Question(int questionTextId, boolean answerTrue) {
        mQuestionTextId = questionTextId;
        mIsAnswerTrue = answerTrue;
    }

    public int getQuestionTextId() {
        return mQuestionTextId;
    }

    public void setQuestionTextId(int questionTextId) {
        mQuestionTextId = questionTextId;
    }

    public boolean isAnswerTrue() {
        return mIsAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mIsAnswerTrue = answerTrue;
    }
}
