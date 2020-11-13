package com.example.cardreader;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cardreader.databinding.FragmentCardDetailBinding;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardDetailFragment extends Fragment implements CardImageDisplayer {
    private FragmentCardDetailBinding binding;

    public static CardDetailFragment newInstance() { return new CardDetailFragment(); }

    public static CardDetailFragment
    getOrCreateCardDetailFragment(FragmentManager fragmentManager) {
        CardDetailFragment cardDetailFragment =
                (CardDetailFragment) fragmentManager.
                        findFragmentById(R.id.card_detail_fragment_container);
        if (cardDetailFragment == null) {
            cardDetailFragment = CardDetailFragment.newInstance();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(
                    R.id.card_detail_fragment_container, cardDetailFragment);
            fragmentTransaction.commitNow();
        }
        return cardDetailFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_card_detail, container, false);
        return binding.getRoot();
    }

    @Override
    public void displayCard(Card card) {
        Picasso.get()
                .load(card.getCardImageURL())
                .into(binding.cardImage);
    }
}