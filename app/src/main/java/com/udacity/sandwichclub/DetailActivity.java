package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    public static final String NO_VALUE = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich s) {
        TextView placeOfOriginView;
        placeOfOriginView = findViewById(R.id.place_of_origin_tv);
        String placeOfOriginText = s.getPlaceOfOrigin();
        placeOfOriginView.append(isEmpty(placeOfOriginText));

        TextView descriptionView;
        descriptionView = findViewById(R.id.description_tv);
        String descriptionText = s.getDescription();
        descriptionView.append(isEmpty(descriptionText));

//        TextView ingredientsView;
//        ingredientsView = findViewById(R.id.ingredients_tv);
//        String ingredientsText = buildIngredientString(s.getIngredients());
//        ingredientsText =  (ingredientsText.equals("")) ?   "N/A" : ingredientsText;
//        ingredientsView.append(ingredientsText);
//
//        TextView alsoKnownAsView;
//        alsoKnownAsView = findViewById(R.id.also_known_tv);
//        String alsoKnownAsText = buildAKAString(s.getAlsoKnownAs());
//        Log.d("AKA >>>>>>>>>>>>", alsoKnownAsText);
//        //alsoKnownAsText =  (alsoKnownAsText.equals("")) ?  "N/A" : alsoKnownAsText;
//        //alsoKnownAsView.append(alsoKnownAsText);
    }

    private String isEmpty(String s){
        Boolean emptyString;
        String resultString;
        emptyString =  s.equals("");
        resultString = emptyString?  "N/A" : s;
        return resultString;
    }


    //to do: make more generic
    private String buildIngredientString(List<String> l){
        try {
            int ingredientsListLength = l.size();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ingredientsListLength - 2; i++) {
                sb.append(l.get(i));
                sb.append("\n");
            }
            sb.append(l.get(ingredientsListLength - 1));
            return sb.toString();
        } catch (Error e) {
            Log.e("OOPS1", "Error in buildIngredientString", e);}
        return "";
    }

    private String buildAKAString(List<String> l){
            try {
                int akaListLength =  l.size();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < akaListLength - 2; i++)
                {
                    sb.append(l.get(i));
                    sb.append(" / ");
                }
                sb.append(l.get(akaListLength - 1));
                return sb.toString();
            } catch (Error e) {
                Log.e("OOPS2", "Error in parsing buildAKAString", e);
            }
            return "";
        }
}