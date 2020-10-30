package com.example.cardreader;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardListFragment extends Fragment {

    private static final String CARDLIST_PARAM = "CARDLIST_PARAM";
    private CardListAdapter mCardListAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CardList cardList = new CardList();

    public static CardListFragment newInstance(CardList cardList) {
        CardListFragment fragment = new CardListFragment();
        Bundle args = new Bundle();
        args.putSerializable(CARDLIST_PARAM, cardList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardList =
                    new CardList((ArrayList<Card>) getArguments().getSerializable(CARDLIST_PARAM));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_card_list, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mCardListAdapter = new CardListAdapter(view.getContext(), cardList);
        mRecyclerView.setAdapter(mCardListAdapter);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }
}