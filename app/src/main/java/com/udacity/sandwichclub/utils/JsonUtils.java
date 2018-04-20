package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    // got this from @mbeevor; really need to brush up java skills
    public static final String NAME = "name";
    public static final String MAIN_NAME = "mainName";
    public static final String ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String INGREDIENTS = "ingredients";
    public static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";

    public static final String TAG = "JSONUtils";


    public static Sandwich parseSandwichJson(String json) {
        // Java way: best practice to declare and assign vars separately?
        JSONObject sandwichJSON;
        String mainName = "";
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin = "";
        String description = "";
        String image = "";
        List<String> ingredients = new ArrayList<>();
        try {
            sandwichJSON = new JSONObject(json);
            JSONObject sandwichName = sandwichJSON.getJSONObject(NAME);
            mainName = sandwichName.getString(MAIN_NAME);
            placeOfOrigin = sandwichJSON.getString(PLACE_OF_ORIGIN);
            description = sandwichJSON.getString(DESCRIPTION);
            image = sandwichJSON.getString(IMAGE);
            // The trickier ones with JSONArray
            JSONArray alsoKnownAsArray = sandwichName.getJSONArray(ALSO_KNOWN_AS);
            if (alsoKnownAsArray.length() > 0){
                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                    String alsoKnownAsString = alsoKnownAsArray.getString(i);
                    alsoKnownAs.add(alsoKnownAsString);
                }
            }
            JSONArray ingredientsArray = sandwichJSON.getJSONArray(INGREDIENTS);
            for (int j = 0; j < ingredientsArray.length(); j++) {
                String ingredientsString = ingredientsArray.getString(j);
                ingredients.add(ingredientsString);
            }
            // what is better; contructor like this or make a new sandwich instance and use setters?
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin,
                    description, image, ingredients);
            } catch (JSONException e) {
            Log.e(TAG, "Error in parsing Json file", e);
            return null;
        }
    }
}
