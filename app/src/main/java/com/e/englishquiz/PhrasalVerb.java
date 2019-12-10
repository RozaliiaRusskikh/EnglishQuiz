package com.e.englishquiz;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class PhrasalVerb extends AppCompatActivity {

    private UUID mId;
    private String mVerb;
    private String mMeaning;
    private String mExample;
    private boolean mKnown;

    public PhrasalVerb() {
        mId = UUID.randomUUID();
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
g