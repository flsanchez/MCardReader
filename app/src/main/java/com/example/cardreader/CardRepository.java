package com.example.cardreader;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

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

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final CardDao mAsyncTaskDao;

        deleteAllAsyncTask(CardDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public void update (Card card) {
        new updateAsyncTask(mDao).execute(card);
    }

    private static class updateAsyncTask extends AsyncTask<Card, Void, Void> {

        private final CardDao mAsyncTaskDao;

        updateAsyncTask(CardDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Card... params) {
            Card card = params[0];
            mAsyncTaskDao.update(card.getId(), card.getFavourite());
            return null;
        }
    }

}
