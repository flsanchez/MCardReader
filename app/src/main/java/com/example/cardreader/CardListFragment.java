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

    public static final String FAVORITE_PARAM = "FAVORITE_PARAM";
    private CardListAdapter mCardListAdapter;
    private ArrayList<Card> cardList = new ArrayList<>();
    private FragmentCardListBinding binding;
    public CardViewModel mCardViewModel;
    private Boolean showFavorite;

    public static CardListFragment newInstance(Boolean showFavorite) {
        CardListFragment fragment = new CardListFragment();
        Bundle args = new Bundle();
        args.putBoolean(FAVORITE_PARAM, showFavorite);
        fragment.setArguments(args);
        return fragment;
    }

    private void initializeCardViewModel() {
        if (mCardViewModel == null) {
            mCardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showFavorite = getArguments().getBoolean(FAVORITE_PARAM);

        initializeCardViewModel();
        mCardListAdapter = new CardListAdapter(
                this.getContext(), this, this);
        mCardListAdapter.setShowFavorite(showFavorite);
        mCardListAdapter.setCardList(cardList);

        if (shouldInitializeCardDetailFragment()) {
            initializeCardDetailFragment();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        updateCardListObserve(showFavorite);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_card_list, container, false);
        binding.recyclerView.setAdapter(mCardListAdapter);
        return binding.getRoot();
    }

    @Override
    public void displayCard(Card card) {
        if (shouldDisplayCardLandscape()) {
            displayCardLandscape(card);
        } else {
            displayCardPortrait(card);
        }
    }

    public void initializeCardDetailFragment() {
        CardDetailFragment.createCardDetailFragment(getFragmentManager());
    }

    public Boolean shouldDisplayCardLandscape() {
        return isScreenLandscape();
    }

    public Boolean shouldInitializeCardDetailFragment() {
        return isScreenLandscape();
    }

    public Boolean isScreenLandscape() {
        int screen_orientation = getResources().getConfiguration().orientation;
        return screen_orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void displayCardPortrait(Card card) {
        Context context = getActivity();
        Intent intent = new Intent(context, CardDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CardDetailActivity.KEY_CARD, card);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private void displayCardLandscape(Card card) {
        CardDetailFragment.getCardDetailFragment(getFragmentManager()).displayCard(card);
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