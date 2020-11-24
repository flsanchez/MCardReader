package com.example.cardreader;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.core.app.ApplicationProvider;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import static org.mockito.ArgumentMatchers.any;


import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(RobolectricTestRunner.class)
public class CardListAdapterUnitTest {
    private Context mockContext = ApplicationProvider.getApplicationContext();

    private Card createCard(){
        return new Card(any(),any(),any());
    }

    private ArrayList<Card> createCardList() {
        return new ArrayList<>(Arrays.asList(createCard(), createCard()));
    }

    @Test
    public void getCountWithCardListNonEmpty() {
        CardListAdapter adapter = new CardListAdapter(mockContext, any(), any());
        adapter.setCardList(createCardList());
        assertEquals(adapter.getItemCount(), 2);
    }
}