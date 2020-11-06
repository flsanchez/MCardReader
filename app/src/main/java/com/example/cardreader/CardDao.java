package com.example.cardreader;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CardDao {
    @Insert
    void insert(Card card);

    @Query("DELETE FROM card_table")
    void deleteAll();

    @Query("SELECT * from card_table ORDER BY id ASC")
    LiveData<List<Card>> getAllCards();

    @Query("SELECT * from card_table LIMIT 1")
    Card[] getAnyCard();
// TODO revise favorites
//    @Query("UPDATE card_table SET is_favourite=:isFavourite WHERE id=:id")
//    void update(int id, Boolean isFavourite);
}
