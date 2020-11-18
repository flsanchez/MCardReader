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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);

        initializeCardListFragment();

        binding.fabFavorites.setOnClickListener((view) -> {
            showFavorite = !showFavorite;
            getCardListFragment().toggleFavoriteList(showFavorite);
        });
    }

    private void initializeCardListFragment() {
        CardListFragment cardListFragment = getCardListFragment();
        if (cardListFragment == null) {
            cardListFragment = CardListFragment.newInstance(showFavorite);
            getSupportFragmentManager().
                    beginTransaction().
                    add(R.id.fragment_recycler_container, cardListFragment).
                    commit();
        }
    }

    private CardListFragment getCardListFragment() {
        return (CardListFragment) getSupportFragmentManager().
                findFragmentById(R.id.fragment_recycler_container);
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
                getCardListFragment().clearFavorites();
                return true;
            case R.id.delete_db:
                getCardListFragment().deleteDB();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}