package com.example.cardreader;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CardViewModel extends AndroidViewModel {
    private final CardRepository mRepository;

    public CardViewModel (Application application) {
        super(application);
        mRepository = new CardRepository(application);
    }

    LiveData<List<Card>> getCardList(Boolean filterFavorites) {
        return mRepository.getCardList(filterFavorites);
    }

    public void deleteAll() {mRepository.deleteAll();}

    public void update(Card card) {mRepository.update(card);}

    public void clearFavorites() {mRepository.clearFavorites();}
}