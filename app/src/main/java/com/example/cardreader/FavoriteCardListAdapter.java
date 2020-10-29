package com.example.cardreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY;

public class FavoriteCardListAdapter extends
        RecyclerView.Adapter<FavoriteCardListAdapter.FavoriteCardViewHolder> {
    private final LayoutInflater mInflater;
    private final CardList cardList;
    public StateRestorationPolicy stateRestorationPolicy = PREVENT_WHEN_EMPTY;

    public FavoriteCardListAdapter(Context context, CardList cardList) {
        mInflater = LayoutInflater.from(context);
        this.cardList = cardList;
    }

    class FavoriteCardViewHolder extends AbstractCardViewHolder{

        public FavoriteCardViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        CardList getCardList() {
            return cardList;
        }
    }

    @NonNull
    @Override
    public FavoriteCardViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView =
                mInflater.inflate(R.layout.favorite_recycler_item, parent, false);
        return new FavoriteCardViewHolder(mItemView);
    }


    @Override
    public void onBindViewHolder(@NonNull FavoriteCardViewHolder holder, int position) {
        Card card = cardList.get(position);
        holder.textName.setText(card.getName());
        holder.textText.setText(card.getText());
        holder.mPosition = position;
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
