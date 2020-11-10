package com.example.cardreader;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CardList extends ArrayList<Card> {

    public CardList () {
        super();
    }

    public CardList (List<Card> cardList) { super(cardList); }

    public void addItemsFromJSON(String TAG, InputStream inputStream) {
        try {
            String jsonString = readDataFromJSONFile(inputStream);
            JSONArray jsonDataArray = new JSONArray(jsonString);

            for (int i=0; i<jsonDataArray.length(); i++) {
                JSONObject jsonDataItem = jsonDataArray.getJSONObject(i);

                String name = jsonDataItem.getString("name");
                String text = jsonDataItem.getString("text");
                String scryfallId =
                        jsonDataItem.getJSONObject("identifiers").getString("scryfallId");

                Card card = new Card(name, text, scryfallId);
                this.add(card);
            }

        } catch (JSONException | IOException e) {
            Log.d(TAG, "addItemsFromJSON: ", e);
        }
    }

    private String readDataFromJSONFile(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        try {
            String jsonString;
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while((jsonString = bufferedReader.readLine()) != null)
                builder.append(jsonString);
        } finally {
            if(inputStream != null)
                inputStream.close();
        }
        return new String(builder);
    }
}
