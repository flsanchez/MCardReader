package com.example.cardreader;

import android.os.AsyncTask;

abstract class CardAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private final CardDao mAsyncTaskDao;

    CardAsyncTask(CardDao dao) { mAsyncTaskDao = dao; }

    public CardDao getAsyncTaskDao() {return mAsyncTaskDao;}
}
