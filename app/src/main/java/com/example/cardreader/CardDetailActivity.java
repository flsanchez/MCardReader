package com.example.cardreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.example.cardreader.databinding.ActivityCardDetailBinding;

public class CardDetailActivity extends AppCompatActivity {
    public static final String KEY_CARD = "CARD";
    private final String TAG = "CardDetailActivity";
    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCardDetailBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_card_detail);
        card = (Card) getIntent().getExtras().getSerializable(KEY_CARD);
        initializeCardDetailFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCardDetailFragment().displayCard(card);
    }

    public void initializeCardDetailFragment() {
        CardDetailFragment.createCardDetailFragment(getSupportFragmentManager());
    }

    public CardDetailFragment getCardDetailFragment() {
        return CardDetailFragment.getCardDetailFragment(getSupportFragmentManager());
    }

    public void onClickCardImage(View view) {
        finish();
    }

}