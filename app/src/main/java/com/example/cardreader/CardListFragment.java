package com.example.cardreader;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cardreader.databinding.FragmentCardListBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardListFragment extends Fragment implements CardImageDisplayer, CardFavoriteManager {

    private static final String FAVORITE_PARAM = "FAVORITE_PARAM";
    private CardListAdapter mCardListAdapter;
    private CardList cardList = new CardList();
    private FragmentCardListBinding binding;
    private CardViewModel mCardViewModel;

    public static CardListFragment newInstance(Boolean showFavorite) {
        CardListFragment fragment = new CardListFragment();
        Bundle args = new Bundle();
        args.putBoolean(FAVORITE_PARAM, showFavorite);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Boolean showFavorite = getArguments().getBoolean(FAVORITE_PARAM);

        mCardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
        mCardListAdapter = new CardListAdapter(
                this.getContext(),this, this);
        mCardListAdapter.setShowFavorite(showFavorite);
        mCardListAdapter.setCardList(cardList);

        updateCardListObserve(showFavorite);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(
                inflater, R.layout.fragment_card_list, container, false);
        binding.recyclerView.setAdapter(mCardListAdapter);
        return binding.getRoot();
    }

    @Override
    public void displayCard(Card card) {
        if (isScreenPortrait()) {
            displayCardPortrait(card);
        } else {
            displayCardLandscape(card);
        }
    }

    public Boolean isScreenPortrait() {
        int screen_orientation = this.getResources().getConfiguration().orientation;
        return screen_orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public void displayCardPortrait(Card card) {
        Context context = this.getActivity();
        Intent intent = new Intent(context, CardDetailActivity.class);
        intent.putExtra(CardDetailActivity.KEY_CARD_URL, card.getCardImageURL());
        context.startActivity(intent);
    }

    public void displayCardLandscape(Card card) {
        Context context = this.getActivity();
        FragmentManager fragmentManager =
                ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CardDetailFragment cardDetailFragment =
                CardDetailFragment.newInstance(card.getCardImageURL());
        fragmentTransaction.replace(
                R.id.card_detail_land_fragment_container, cardDetailFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void toggleFavoriteCard(Card card) {
        card.setFavourite(!card.getFavourite());
        mCardViewModel.update(card);
        mCardListAdapter.setCardList(cardList);
    }

    public void toggleFavoriteList(Boolean showFavorite) {
        updateCardListObserve(showFavorite);
        mCardListAdapter.setShowFavorite(showFavorite);
    }

    private void updateCardListObserve(Boolean showFavorite) {
        mCardViewModel.getCardList(showFavorite).observe(
                this, (List<Card> cardListUpdated) -> {
                    cardList = new CardList(cardListUpdated);
                    mCardListAdapter.setCardList(cardList);
        });
    }
}