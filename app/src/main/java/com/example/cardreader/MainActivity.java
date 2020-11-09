package com.example.cardreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Boolean showFavorite = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardList cardList = new CardList();
        cardList.addItemsFromJSON(TAG, getResources().openRawResource(R.raw.zen));

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        CardListFragment cardListFragment = CardListFragment.newInstance(cardList, showFavorite);
        fragmentTransaction.add(R.id.fragment_recycler_container, cardListFragment);
        fragmentTransaction.commit();

        int screen_orientation = getResources().getConfiguration().orientation;
        if (screen_orientation == Configuration.ORIENTATION_LANDSCAPE) {
            cardListFragment.displayCardLandscape(cardList.get(0), this);
        }

        FloatingActionButton fab = findViewById(R.id.fab_favorites);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFavorite = !showFavorite;
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                CardListFragment cardListFragment =
                        CardListFragment.newInstance(
                                showFavorite ? cardList : cardList.getFavorites(), showFavorite);
                fragmentTransaction.replace(R.id.fragment_recycler_container, cardListFragment);
                fragmentTransaction.commit();
            }
        });
    }
}