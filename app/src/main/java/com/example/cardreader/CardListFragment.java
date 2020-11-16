package com.example.cardreader;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cardreader.databinding.FragmentCardListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardListFragment extends Fragment implements CardImageDisplayer, CardFavoriteManager {

    private static final String FAVORITE_PARAM = "FAVORITE_PARAM";
    private CardListAdapter mCardListAdapter;
    private ArrayList<Card> cardList = new ArrayList<>();
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

    private void displayCardPortrait(Card card) {
        Context context = this.getActivity();
        Intent intent = new Intent(context, CardDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CardDetailActivity.KEY_CARD, card);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private void displayCardLandscape(Card card) {
        CardDetailFragment.getOrCreateCardDetailFragment(getFragmentManager()).displayCard(card);
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
                    cardList = new ArrayList<>(cardListUpdated);
                    mCardListAdapter.setCardList(cardList);
        });
    }

    public void deleteDB() {
        mCardViewModel.deleteAll();
    }

    public void clearFavorites() {
        mCardViewModel.clearFavorites();
    }
}