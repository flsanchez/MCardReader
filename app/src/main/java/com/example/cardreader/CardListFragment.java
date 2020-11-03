package com.example.cardreader;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cardreader.databinding.FragmentCardListBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardListFragment extends Fragment implements CardImageDisplayer {

    private static final String CARDLIST_PARAM = "CARDLIST_PARAM";
    private static final String FAVORITE_PARAM = "FAVORITE_PARAM";
    private CardListAdapter mCardListAdapter;
    private LinearLayoutManager mLayoutManager;
    private CardList cardList = new CardList();
    private Boolean showFavorite;
    private FragmentCardListBinding binding;

    public static CardListFragment newInstance(CardList cardList, Boolean showFavorite) {
        CardListFragment fragment = new CardListFragment();
        Bundle args = new Bundle();
        args.putSerializable(CARDLIST_PARAM, cardList);
        args.putBoolean(FAVORITE_PARAM, showFavorite);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardList =
                    new CardList((ArrayList<Card>) getArguments().getSerializable(CARDLIST_PARAM));
            showFavorite = getArguments().getBoolean(FAVORITE_PARAM);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(
                inflater, R.layout.fragment_card_list, container, false);
        binding.recyclerView.setHasFixedSize(true);
        mCardListAdapter = new CardListAdapter(
                this.getContext(), cardList, showFavorite,this);
        binding.recyclerView.setAdapter(mCardListAdapter);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);

        return binding.getRoot();
    }

    @Override
    public void displayCardImage(Context context, String cardUrl, int screen_orientation) {
        if (screen_orientation == Configuration.ORIENTATION_PORTRAIT) {
            Intent intent = new Intent(context, CardDetailActivity.class);
            intent.putExtra(CardDetailActivity.KEY_CARD_URL, cardUrl);
            context.startActivity(intent);
        } else {
            FragmentManager fragmentManager =
                    ((AppCompatActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            CardDetailFragment cardDetailFragment = CardDetailFragment.newInstance(cardUrl);
            fragmentTransaction.replace(
                    R.id.card_detail_land_fragment_container, cardDetailFragment);
            fragmentTransaction.commit();
        }
    }
}