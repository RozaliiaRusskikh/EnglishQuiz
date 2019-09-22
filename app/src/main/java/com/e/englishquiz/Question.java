package com.e.englishquiz;

public class Question {
    /*The Question class holds two pieces of data:
    the question text and the question answer (true or false)*/
    private int mQuestionTextId; // the variable holds the resource ID of a string resource for yhe question
    private boolean mIsAnswerTrue;

    public Question(int questionTextId, boolean answerTrue) { // the Question constructor
        mQuestionTextId = questionTextId;
        mIsAnswerTrue = answerTrue;
    }
// generating getters and setters

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
