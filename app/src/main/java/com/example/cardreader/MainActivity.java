package com.example.cardreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.cardreader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Boolean showFavorite = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);

        CardListFragment cardListFragment = getOrCreateCardListFragment();
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_recycler_container, cardListFragment).
                commit();

        binding.fabFavorites.setOnClickListener((view) -> {
            showFavorite = !showFavorite;
            getOrCreateCardListFragment().toggleFavoriteList(showFavorite);
        });
    }

    private CardListFragment getOrCreateCardListFragment() {
        CardListFragment cardListFragment =
                (CardListFragment) getSupportFragmentManager().
                        findFragmentById(R.id.fragment_recycler_container);
        if (cardListFragment == null) {
            cardListFragment = CardListFragment.newInstance(showFavorite);
        }
        return cardListFragment;
    }
}