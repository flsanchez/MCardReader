package com.example.cardreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.cardreader.databinding.ActivityCardDetailBinding;

public class CardDetailActivity extends AppCompatActivity {
    public static final String KEY_CARD = "CARD";
    private final String TAG = "CardDetailActivity";
    private CardDetailFragment cardDetailFragment;
    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCardDetailBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_card_detail);
        card = (Card) getIntent().getExtras().getSerializable(KEY_CARD);
        cardDetailFragment = getOrCreateCardDetailFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cardDetailFragment.displayCard(card);
    }

    private CardDetailFragment getOrCreateCardDetailFragment() {
        CardDetailFragment cardDetailFragment =
                (CardDetailFragment) getSupportFragmentManager().
                        findFragmentById(R.id.card_detail_fragment_container);
        if (cardDetailFragment == null) {
            cardDetailFragment = CardDetailFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(
                    R.id.card_detail_fragment_container, cardDetailFragment);
            fragmentTransaction.commit();
        }
        return cardDetailFragment;
    }

    public void onClickCardImage(View view) {
        finish();
    }


}