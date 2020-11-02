package com.example.cardreader;


import android.content.Context;

public interface CardImageDisplayer {
    void displayCardImage(Context context, String cardUrl, int screen_orientation);
}
