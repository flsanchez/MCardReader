package com.example.cardreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class CardDetailActivity extends AppCompatActivity {
    public static final String KEY_CARD_URL = "CARD_URL";
    private final String TAG = "CardDetailActivity";
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        Bundle extras = getIntent().getExtras();
        String cardURL = extras.getString(KEY_CARD_URL);
        Log.d(TAG, cardURL);
        mImageView = findViewById(R.id.card_image);
        Picasso.get()
                .load(cardURL)
                .fit()
                .centerCrop()
                .into(mImageView);
    }

    public void onClickCardImage(View view) {
        finish();
    }

}