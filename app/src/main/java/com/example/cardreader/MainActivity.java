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

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CardListFragment cardListFragment = CardListFragment.newInstance(showFavorite);
        fragmentTransaction.add(R.id.fragment_recycler_container, cardListFragment);
        fragmentTransaction.commit();

        FloatingActionButton fab = findViewById(R.id.fab_favorites);

        fab.setOnClickListener((view) -> {
            showFavorite = !showFavorite;
            cardListFragment.setShowFavorite(showFavorite);
        });
    }
}