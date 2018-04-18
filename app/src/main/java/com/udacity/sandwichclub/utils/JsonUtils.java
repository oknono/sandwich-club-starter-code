package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import org.json.JSONObject;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String sandwich_json) {
        // we are going to want to check if we already have a sandwich?
        // how to deal with String Lists (AKA and Ingredients)
        Sandwich chosenSandwich = new Sandwich();
        try {
            JSONObject sandwich = new JSONObject(sandwich_json);
            JSONObject name = sandwich.getJSONObject("name");
            String MainName = name.getString("mainName");
            //String AlsoKnownAs = name.getString("alsoKnownAs");
            String Image = sandwich.getString("image");
            String PlaceOfOrigin = sandwich.getString("placeOfOrigin");
            String Description = sandwich.getString("description");
            chosenSandwich.setMainName(MainName);
            //chosenSandwich.setAlsoKnownAs(AlsoKnownAs);
            chosenSandwich.setImage(Image);
            chosenSandwich.setPlaceOfOrigin(PlaceOfOrigin);
            chosenSandwich.setDescription(Description);
            //chosenSandwich.setIngredients(Ingredients);

        } catch (JSONException e){
            Log.e("JSON_ERROR", "Parsing went tipsy turvy");
        }
        return chosenSandwich;
    }
}
