package com.example.cardreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardList cardList = new CardList();
        cardList.addItemsFromJSON(TAG, getResources().openRawResource(R.raw.zen));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CardListFragment cardListFragment = CardListFragment.newInstance(cardList);
        fragmentTransaction.add(R.id.fragment_recycler_container, cardListFragment);
        fragmentTransaction.commit();

        FloatingActionButton fab = findViewById(R.id.fab_favorites);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent favoriteIntent = new Intent(
                        MainActivity.this, FavoriteActivity.class);
                favoriteIntent.putExtra(FavoriteActivity.EXTRA_CARD_LIST, cardList.getFavorites());
                startActivity(favoriteIntent);
            }
        });
    }
}