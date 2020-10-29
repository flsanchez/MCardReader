package com.example.cardreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class CardDetailActivity extends AppCompatActivity {
    public static final String KEY_CARD_URL = "CARD_URL";
    private final String TAG = "CardDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        Bundle extras = getIntent().getExtras();
        String cardURL = extras.getString(KEY_CARD_URL);
        Log.d(TAG, cardURL);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CardDetailFragment cardDetailFragment = CardDetailFragment.newInstance(cardURL);
        fragmentTransaction.add(R.id.card_detail_fragment_container, cardDetailFragment);
        fragmentTransaction.commit();
    }

    public void onClickCardImage(View view) {
        finish();
    }

}