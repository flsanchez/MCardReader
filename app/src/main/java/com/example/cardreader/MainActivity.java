package com.example.cardreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private CardList cardList = new CardList();
    private static final String TAG = "MainActivity";
    private CardListAdapter mCardListAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mCardListAdapter = new CardListAdapter(this, cardList);
        mRecyclerView.setAdapter(mCardListAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        cardList.addItemsFromJSON(TAG, getResources().openRawResource(R.raw.zen));
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