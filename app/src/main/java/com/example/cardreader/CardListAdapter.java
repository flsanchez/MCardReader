package com.example.cardreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardreader.databinding.CardRecyclerItemBinding;

import static
        androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {
    private final LayoutInflater mInflater;
    private CardList cardList;
    public StateRestorationPolicy stateRestorationPolicy = PREVENT_WHEN_EMPTY;
    private final Boolean showFavorite;
    private final CardImageDisplayer cardImageDisplayer;
    private final CardFavoriteManager cardFavoriteManager;
    private CardRecyclerItemBinding binding;

    public CardListAdapter(Context context,
                           CardList cardList,
                           Boolean showFavorite,
                           CardImageDisplayer cardImageDisplayer,
                           CardFavoriteManager cardFavoriteManager) {
        mInflater = LayoutInflater.from(context);
        this.cardList = cardList;
        this.showFavorite = showFavorite;
        this.cardImageDisplayer = cardImageDisplayer;
        this.cardFavoriteManager = cardFavoriteManager;
    }

    public void setCardList(CardList cardList) {
        this.cardList = cardList;
        notifyDataSetChanged();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder{
        final CardFavoriteManager cardFavoriteManager;
        final CardImageDisplayer cardImageDisplayer;
        private final Boolean showFavorite;
        private CardRecyclerItemBinding binding;

        public CardViewHolder(
                @NonNull CardRecyclerItemBinding binding,
                CardImageDisplayer cardImageDisplayer,
                CardFavoriteManager cardFavoriteManager,
                Boolean showFavorite
        ) {
            super(binding.getRoot());
            this.binding = binding;
            this.cardImageDisplayer = cardImageDisplayer;
            this.cardFavoriteManager = cardFavoriteManager;
            this.showFavorite = showFavorite;
        }

        public void bindModel(Card model) {
            binding.name.setText(model.getName());
            binding.text.setText(model.getText());
            binding.imageButton.setOnClickListener((view) ->cardImageDisplayer.displayCard(model));
            if (showFavorite) {
                binding.favoriteButton.setImageResource(
                        model.getFavourite() ? android.R.drawable.btn_star_big_on :
                                android.R.drawable.btn_star_big_off
                );
                binding.favoriteButton.setOnClickListener(
                        (view) -> cardFavoriteManager.toggleFavoriteCard(model));
            } else {
                binding.favoriteButton.setVisibility(View.GONE);
            }
        }
    }

    @NonNull
    @Override
    public CardListAdapter.CardViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(
                mInflater, R.layout.card_recycler_item, parent, false);
        return new CardViewHolder(binding, cardImageDisplayer, cardFavoriteManager, showFavorite);
    }

    @Override
    public void onBindViewHolder(@NonNull CardListAdapter.CardViewHolder holder, int position) {
        holder.bindModel(cardList.get(position));
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}