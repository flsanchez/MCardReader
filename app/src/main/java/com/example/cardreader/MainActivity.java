package com.example.cardreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.cardreader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Boolean showFavorite = true;
    private CardListFragment cardListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);

        cardListFragment = getOrCreateCardListFragment();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_favorites:
                cardListFragment.clearFavorites();
                return true;
            case R.id.delete_db:
                cardListFragment.deleteDB();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}