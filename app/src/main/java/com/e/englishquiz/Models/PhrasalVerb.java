package com.e.englishquiz.Models;

public class PhrasalVerb {

    private int mId;
    private String mVerb;
    private String mMeaning;
    private String mExample;
    private boolean mKnown;

    public PhrasalVerb(int id, String verb, String meaning, String example, Boolean known) {
      mId = id;
      mVerb = verb;
      mMeaning = meaning;
      mExample = example;
      mKnown = known;
    }


    public int getId() {
        return mId;
    }

    public String getVerb() {
        return mVerb;
    }

    public String getMeaning() {
        return mMeaning;
    }

    public String getExample() {
        return mExample;
    }

    public boolean isKnown() {
        return mKnown;
    }

    public void setKnown(boolean known) {
        mKnown = known;
    }
}
