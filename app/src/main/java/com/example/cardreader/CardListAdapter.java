package com.example.cardreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static
        androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {
    private final LayoutInflater mInflater;
    private final CardList cardList;
    public StateRestorationPolicy stateRestorationPolicy = PREVENT_WHEN_EMPTY;
    private final Boolean showFavorite;
    private final CardImageDisplayer cardImageDisplayer;

    public CardListAdapter(Context context, CardList cardList, Boolean showFavorite,
                           CardImageDisplayer cardImageDisplayer) {
        mInflater = LayoutInflater.from(context);
        this.cardList = cardList;
        this.showFavorite = showFavorite;
        this.cardImageDisplayer = cardImageDisplayer;
    }

    class CardViewHolder extends RecyclerView.ViewHolder{
        public final TextView textName;
        public final TextView textText;
        public int mPosition;
        public final ImageView imageShow;
        public final ImageView favoriteButton;
        final CardListAdapter mAdapter;
        final CardImageDisplayer cardImageDisplayer;

        public CardViewHolder(@NonNull View itemView, CardListAdapter adapter) {
            super(itemView);
            textName = itemView.findViewById(R.id.name);
            textText = itemView.findViewById(R.id.text);
            imageShow = itemView.findViewById(R.id.image_button);
            this.mAdapter = adapter;
            this.cardImageDisplayer = adapter.cardImageDisplayer;
            favoriteButton = itemView.findViewById(R.id.favorite_button);
            imageShow.setOnClickListener(this::onClickShowImage);
            if (showFavorite) {
                favoriteButton.setOnClickListener(this::onClickFavorite);
            } else {
                favoriteButton.setVisibility(View.GONE);
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
        View mItemView = mInflater.inflate(R.layout.recycler_item, parent, false);
        return new CardViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CardListAdapter.CardViewHolder holder, int position) {
        Card card = cardList.get(position);
        holder.textName.setText(card.getName());
        holder.textText.setText(card.getText());
        holder.mPosition = position;
        holder.favoriteButton.setImageResource(
                card.getFavourite() ?
                        android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}