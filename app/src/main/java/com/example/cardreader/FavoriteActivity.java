package com.example.cardreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    private static final String TAG = "FavoriteActivity";
    private FavoriteCardListAdapter mCardListAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    public static final String EXTRA_CARD_LIST = "EXTRA_CARD_LIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Bundle extras = getIntent().getExtras();
        CardList favoriteCardList =
                new CardList((ArrayList<Card>) extras.getSerializable(EXTRA_CARD_LIST));
        mRecyclerView = findViewById(R.id.favorite_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mCardListAdapter = new FavoriteCardListAdapter(this, favoriteCardList);
        mRecyclerView.setAdapter(mCardListAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}