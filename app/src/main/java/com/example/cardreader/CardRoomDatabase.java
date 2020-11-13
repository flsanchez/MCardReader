package com.example.cardreader;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.InputStream;

@Database(entities = {Card.class}, version = 2, exportSchema = false)
public abstract class CardRoomDatabase extends RoomDatabase {
    public abstract CardDao cardDao();

    private static CardRoomDatabase INSTANCE;

    public static CardRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CardRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CardRoomDatabase.class, "card_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    new PopulateDbAsync(INSTANCE, context.getResources()).execute();
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final InputStream cardListStream;
        private final CardDao mDao;

        PopulateDbAsync(CardRoomDatabase db, Resources resources) {
            mDao = db.cardDao();
            cardListStream = resources.openRawResource(R.raw.zen);
        }

        @Override
        protected Void doInBackground(final Void... params) {
            if (mDao.getAnyCard().length < 1) {
                CardList cardList = new CardList();
                cardList.addItemsFromJSON("DB", cardListStream);
                for (int i = 0; i < cardList.size() ; i++) {
                    mDao.insert(cardList.get(i));
                }
            }
            return null;
        }
    }
}