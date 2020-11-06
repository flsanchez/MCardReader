package com.example.cardreader;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CardViewModel extends AndroidViewModel {
    private CardRepository mRepository;
    private LiveData<List<Card>> mAllWords;

    public CardViewModel (Application application) {
        super(application);
        mRepository = new CardRepository(application);
        mAllWords = mRepository.getAllCards();
    }

    LiveData<List<Card>> getAllWords() { return mAllWords; }

    public void deleteAll() {mRepository.deleteAll();}
}