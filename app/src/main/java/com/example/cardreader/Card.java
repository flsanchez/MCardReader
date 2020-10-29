package com.example.cardreader;

import java.io.Serializable;

public class Card implements Serializable {
    private final String name;
    private final String text;
    private final String scryfallId;
    private Boolean isFavourite = false;

    public Card (String name, String text, String scryfallId) {
        this.name = name;
        this.text = text;
        this.scryfallId = scryfallId;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public String getScryfallId() {
        return scryfallId;
    }

    public String getCardImageURL() {
        return String.format("https://api.scryfall.com/cards/%s?format=image", scryfallId);
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }
}
