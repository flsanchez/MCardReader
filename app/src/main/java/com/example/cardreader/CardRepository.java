package com.example.cardreader;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.xml.transform.Result;

public class CardRepository {
    private final CardDao mDao;

    CardRepository(Application application){
        CardRoomDatabase db = CardRoomDatabase.getDatabase(application);
        mDao = db.cardDao();
    }

    LiveData<List<Card>> getCardList(Boolean filterFavorites) {
        return filterFavorites ? mDao.getAllCards() : mDao.getFavoriteCards();
    }

    public void deleteAll() {new deleteAllAsyncTask(mDao).execute();}

    private static class deleteAllAsyncTask extends CardAsyncTask<Void, Void, Void> {
        deleteAllAsyncTask(CardDao dao) { super(dao); }

        @Override
        protected Void doInBackground(Void... voids) {
            getAsyncTaskDao().deleteAll();
            return null;
        }
    }

    public void update (Card card) {
        new updateAsyncTask(mDao).execute(card);
    }

    private static class updateAsyncTask extends CardAsyncTask<Card, Void, Void> {
        updateAsyncTask(CardDao dao) { super(dao); }

        @Override
        protected Void doInBackground(final Card... params) {
            Card card = params[0];
            getAsyncTaskDao().update(card.getId(), card.getFavourite());
            return null;
        }
    }

}

