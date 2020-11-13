package com.example.cardreader;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
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
    @ColumnInfo(name="is_favourite")
    private Boolean isFavourite;

    public Card (@NonNull String name, @NonNull String text, @NonNull String scryfallId) {
        this.name = name;
        this.text = text;
        this.scryfallId = scryfallId;
        this.isFavourite = false;
    }

    public int getId() {return id;}

    @NonNull
    public String getText() {
        return text;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getScryfallId() { return scryfallId; }

    public String getCardImageURL() {
        return String.format("https://api.scryfall.com/cards/%s?format=image", scryfallId);
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setId(int id) {this.id = id;}

    public void setText(@NonNull String text) {
        this.text = text;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setScryfallId(@NonNull String scryfallId) { this.scryfallId = scryfallId; }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

}
