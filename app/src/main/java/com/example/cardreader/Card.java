package com.example.cardreader;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "card_table")
public class Card implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private int id;

    @NonNull
    @ColumnInfo(name="name")
    private String name;

    @NonNull
    @ColumnInfo(name="text")
    private String text;

    @NonNull
    @ColumnInfo(name="scryfall_id")
    private String scryfallId;

    @NonNull
    @ColumnInfo(name="card_image_url")
    private String cardImageURL;

    @NonNull
    @ColumnInfo(name="is_favourite")
    private Boolean isFavourite;

    @Ignore
    public Card (int id, @NonNull String name, @NonNull String text, @NonNull String scryfallId) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.scryfallId = scryfallId;
        this.cardImageURL =
                String.format("https://api.scryfall.com/cards/%s?format=image", scryfallId);
        this.isFavourite = false;
    }

    public Card (@NonNull String name, @NonNull String text, @NonNull String scryfallId) {
        this.name = name;
        this.text = text;
        this.scryfallId = scryfallId;
        this.cardImageURL =
                String.format("https://api.scryfall.com/cards/%s?format=image", scryfallId);
        this.isFavourite = false;
    }

    public int getId() {return id;}

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public String getScryfallId() {return scryfallId;}

    public String getCardImageURL() { return cardImageURL; }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setId(int id) {this.id = id;}

    public void setCardImageURL(String cardImageURL) { this.cardImageURL = cardImageURL; }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }
}
