package com.example.cardreader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static
        androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {
    private final LayoutInflater mInflater;
    private final CardList cardList;
    public StateRestorationPolicy stateRestorationPolicy = PREVENT_WHEN_EMPTY;

    public CardListAdapter(Context context, CardList cardList) {
        mInflater = LayoutInflater.from(context);
        this.cardList = cardList;
    }

    class CardViewHolder extends AbstractCardViewHolder{
        public final ImageView favoriteButton;
        final CardListAdapter mAdapter;

        public CardViewHolder(@NonNull View itemView, CardListAdapter adapter) {
            super(itemView);
            this.mAdapter = adapter;
            favoriteButton = itemView.findViewById(R.id.favorite_button);
            favoriteButton.setOnClickListener(this::onClickFavorite);
        }

        @Override
        CardList getCardList() {
            return cardList;
        }

        private void onClickFavorite(View v) {
            Card card = getCardList().get(mPosition);
            card.setFavourite(!card.getFavourite());
            mAdapter.notifyItemChanged(mPosition);
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


abstract class AbstractCardViewHolder extends RecyclerView.ViewHolder{
    public final TextView textName;
    public final TextView textText;
    public int mPosition;
    public final ImageView imageShow;
    public final ImageView favoriteButton;

    public AbstractCardViewHolder(@NonNull View itemView) {
        super(itemView);
        textName = itemView.findViewById(R.id.name);
        textText = itemView.findViewById(R.id.text);
        imageShow = itemView.findViewById(R.id.image_button);
        favoriteButton = itemView.findViewById(R.id.favorite_button);
        imageShow.setOnClickListener(this::onClickShowImage);
    }

    public void onClickShowImage(View v) {
        String cardURL = getCardList().get(mPosition).getCardImageURL();

        Context context = v.getContext();
        Intent intent = new Intent(context, CardDetailActivity.class);
        intent.putExtra(CardDetailActivity.KEY_CARD_URL, cardURL);
        context.startActivity(intent);
    }

    abstract CardList getCardList();
}