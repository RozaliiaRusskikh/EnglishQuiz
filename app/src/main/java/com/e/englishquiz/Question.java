package com.e.englishquiz;

public class Question {
    /*The Question class holds two pieces of data:
    the question text and the question answer (true or false)*/
    private int mId;
    private String mQuestionText;
    private boolean mIsAnswerTrue;

    public Question(int id, String questionText, boolean answerTrue) { // the Question constructor
        mId = id;
        mQuestionText = questionText;
        mIsAnswerTrue = answerTrue;
    }

    public int getId() {
        return mId;
    }

    public String getQuestionText() {
        return mQuestionText;
    }

    public void setQuestionText(String questionText) {
        mQuestionText = questionText;
    }

    public boolean isAnswerTrue() {
        return mIsAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mIsAnswerTrue = answerTrue;
    }
}
