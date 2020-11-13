package com.example.cardreader;

import android.app.Application;

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

    public void deleteAll() { new deleteAllAsyncTask(mDao).execute(); }

    public void update(Card card) { new updateAsyncTask(mDao).execute(card); }

    public void clearFavorites() { new clearFavoritesAsyncTask(mDao).execute(); }

    private static class deleteAllAsyncTask extends CardAsyncTask<Void, Void, Void> {
        deleteAllAsyncTask(CardDao dao) { super(dao); }

        @Override
        protected Void doInBackground(Void... voids) {
            getAsyncTaskDao().deleteAll();
            return null;
        }
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

    private static class clearFavoritesAsyncTask extends CardAsyncTask<Void, Void, Void> {
        clearFavoritesAsyncTask(CardDao dao) { super(dao); }

        @Override
        protected Void doInBackground(final Void... params) {
            getAsyncTaskDao().clearFavorites();
            return null;
        }
    }
}

