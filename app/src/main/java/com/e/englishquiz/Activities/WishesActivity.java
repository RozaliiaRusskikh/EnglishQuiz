package com.e.englishquiz.Activities;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e.englishquiz.R;

public class WishesActivity extends AppCompatActivity {

    private ImageView mWishImageView;
    private TextView mWishTextView;
    private Button mMenuButton;
    private Double mPercentResultDouble;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String percentResult = getIntent().getStringExtra("result");
        mPercentResultDouble = Double.parseDouble(percentResult);

        mWishImageView = (ImageView) findViewById(R.id.wish_image);
        mWishTextView = (TextView) findViewById(R.id.wish_text);

        if (mPercentResultDouble == 0) {
            mWishImageView.setImageResource(R.drawable.img_wishes_for_0_percent);
            mWishTextView.setText(R.string.wishes_for_0_percent);

        } else if (mPercentResultDouble > 0 && mPercentResultDouble <= 10) {
            mWishImageView.setImageResource(R.drawable.img_wishes_for_10_percent);
            mWishTextView.setText(R.string.wishes_for_10_percent);

        } else if (mPercentResultDouble > 10 && mPercentResultDouble <= 20) {
            mWishImageView.setImageResource(R.drawable.img_wishes_for_20_percent);
            mWishTextView.setText(R.string.wishes_for_20_percent);

        } else if (mPercentResultDouble > 20 && mPercentResultDouble <= 40) {
            mWishImageView.setImageResource(R.drawable.img_wishes_for_40_percent);
            mWishTextView.setText(R.string.wishes_for_40_percent);

        } else if (mPercentResultDouble > 40 && mPercentResultDouble <= 60) {
            mWishImageView.setImageResource(R.drawable.img_wishes_for_60_percent);
            mWishTextView.setText(R.string.wishes_for_60_percent);

        } else if (mPercentResultDouble > 60 && mPercentResultDouble <= 80) {
            mWishImageView.setImageResource(R.drawable.img_wishes_for_80_percent);
            mWishTextView.setText(R.string.wishes_for_80_percent);

        } else if (mPercentResultDouble > 80 && mPercentResultDouble < 100) {
            mWishImageView.setImageResource(R.drawable.img_wishes_for_100_percent);
            mWishTextView.setText(R.string.wishes_for_100_percent);
        } else if (mPercentResultDouble == 100) {
            mWishImageView.setImageResource(R.drawable.img_wishes_for_winners);
            mWishTextView.setText(R.string.wishes_for_winner);
        }


        mMenuButton = (Button) findViewById(R.id.menu_button);
        mMenuButton.setOnClickListener(new View.OnClickListener() { // setting listeners
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WishesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
