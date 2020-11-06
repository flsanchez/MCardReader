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
    private CardRecyclerItemBinding binding;

    public CardListAdapter(Context context, CardList cardList, Boolean showFavorite,
                           CardImageDisplayer cardImageDisplayer) {
        mInflater = LayoutInflater.from(context);
        this.cardList = cardList;
        this.showFavorite = showFavorite;
        this.cardImageDisplayer = cardImageDisplayer;
    }

    class CardViewHolder extends RecyclerView.ViewHolder{
        public int mPosition;
        final CardListAdapter mAdapter;
        final CardImageDisplayer cardImageDisplayer;
        private CardRecyclerItemBinding binding;

        public CardViewHolder(@NonNull CardRecyclerItemBinding binding, CardListAdapter adapter) {
            super(binding.getRoot());
            this.mAdapter = adapter;
            this.binding = binding;
            this.cardImageDisplayer = adapter.cardImageDisplayer;
            binding.imageButton.setOnClickListener(this::onClickShowImage);
            if (showFavorite) {
                binding.favoriteButton.setOnClickListener(this::onClickFavorite);
            } else {
                binding.favoriteButton.setVisibility(View.GONE);
            }
        }

        private void onClickFavorite(View v) {
            Card card = cardList.get(mPosition);
            card.setFavourite(!card.getFavourite());
            mAdapter.notifyItemChanged(mPosition);
        }

        public void onClickShowImage(View v) {
            String cardURL = cardList.get(mPosition).getCardImageURL();
            int screen_orientation = v.getResources().getConfiguration().orientation;
            cardImageDisplayer.displayCardImage(v.getContext(), cardURL, screen_orientation);
        }

    }

    @NonNull
    @Override
    public CardListAdapter.CardViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(
                mInflater, R.layout.card_recycler_item, parent, false);
        return new CardViewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CardListAdapter.CardViewHolder holder, int position) {
        Card card = cardList.get(position);
        holder.binding.name.setText(card.getName());
        holder.binding.text.setText(card.getText());
        holder.mPosition = position;
        holder.binding.favoriteButton.setImageResource(
                card.getFavourite() ?
                        android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public void updateCardList(CardList cardListUpdated) {
        cardList = cardListUpdated;
        notifyDataSetChanged();
    }
}