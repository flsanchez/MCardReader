package com.example.cardreader;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CardRepository {
    private CardDao mDao;
    private LiveData<List<Card>> mAllCards;

    CardRepository(Application application){
        CardRoomDatabase db = CardRoomDatabase.getDatabase(application);
        mDao = db.cardDao();
        mAllCards = mDao.getAllCards();
    }

    LiveData<List<Card>> getAllCards() {return mAllCards;}

    public void deleteAll() {new deleteAllAsyncTask(mDao).execute();}

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private CardDao mAsyncTaskDao;

        deleteAllAsyncTask(CardDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

// TODO: SOLVE FAVORITE
//    public void update (Card card) {
//        new updateAsyncTask(mDao).execute(card);
//    }
//
//    private static class updateAsyncTask extends AsyncTask<Card, Void, Void> {
//
//        private CardDao mAsyncTaskDao;
//
//        updateAsyncTask(CardDao dao) {
//            mAsyncTaskDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(final Card... params) {
//            mAsyncTaskDao.insert(params[0]);
//            return null;
//        }
//    }

}
