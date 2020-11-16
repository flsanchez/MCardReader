package com.example.cardreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardreader.databinding.CardRecyclerItemBinding;

import java.util.ArrayList;
import java.util.List;

import static
        androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {
    private final LayoutInflater mInflater;
    private ArrayList<Card> cardList;
    public StateRestorationPolicy stateRestorationPolicy = PREVENT_WHEN_EMPTY;
    private Boolean showFavorite = false;
    private final CardImageDisplayer cardImageDisplayer;
    private final CardFavoriteManager cardFavoriteManager;
    private CardRecyclerItemBinding binding;

    public CardListAdapter(Context context,
                           CardImageDisplayer cardImageDisplayer,
                           CardFavoriteManager cardFavoriteManager) {
        mInflater = LayoutInflater.from(context);
        this.cardImageDisplayer = cardImageDisplayer;
        this.cardFavoriteManager = cardFavoriteManager;
    }

    public void setCardList(ArrayList<Card> cardList) {
        this.cardList = cardList;
        notifyDataSetChanged();
    }

    public void setShowFavorite(Boolean showFavorite){
        this.showFavorite = showFavorite;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardListAdapter.CardViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(
                mInflater, R.layout.card_recycler_item, parent, false);
        return new CardViewHolder(binding, cardImageDisplayer, cardFavoriteManager);
    }

    @Override
    public void onBindViewHolder(@NonNull CardListAdapter.CardViewHolder holder, int position) {
        holder.setShowFavorite(showFavorite);
        holder.bindModel(cardList.get(position));
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder{
        final CardFavoriteManager cardFavoriteManager;
        final CardImageDisplayer cardImageDisplayer;
        private Boolean showFavorite;
        private CardRecyclerItemBinding binding;

        public CardViewHolder(
                @NonNull CardRecyclerItemBinding binding,
                CardImageDisplayer cardImageDisplayer,
                CardFavoriteManager cardFavoriteManager
        ) {
            super(binding.getRoot());
            this.binding = binding;
            this.cardImageDisplayer = cardImageDisplayer;
            this.cardFavoriteManager = cardFavoriteManager;
        }

        public void setShowFavorite(Boolean showFavorite){ this.showFavorite = showFavorite; }

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
                binding.favoriteButton.setVisibility(View.VISIBLE);
            } else {
                binding.favoriteButton.setVisibility(View.GONE);
            }
        }
    }
}
