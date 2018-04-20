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
        // to do extract this
        placeOfOriginText =  (placeOfOriginText.equals("")) ?  "N/A" : placeOfOriginText;
        placeOfOriginView.append(placeOfOriginText);

        TextView descriptionView;
        descriptionView = findViewById(R.id.description_tv);
        String descriptionText = s.getDescription();
        descriptionText =  (descriptionText.equals("")) ?   "N/A" : descriptionText;
        descriptionView.append(descriptionText);

        TextView ingredientsView;
        ingredientsView = findViewById(R.id.ingredients_tv);
        String ingredientsText = buildIngredientString(s.getIngredients());
        ingredientsText =  (ingredientsText.equals("")) ?   "N/A" : ingredientsText;
        ingredientsView.append(ingredientsText);

        TextView alsoKnownAsView;
        alsoKnownAsView = findViewById(R.id.also_known_tv);
        String alsoKnownAsText = buildAKAString(s.getAlsoKnownAs());
        alsoKnownAsText =  (alsoKnownAsText.equals("")) ?  "N/A" : alsoKnownAsText;
        alsoKnownAsView.append(alsoKnownAsText);
    }

    //to do: make more generic
    private String buildIngredientString(List<String> l){
        int ingredientsListLength =  l.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ingredientsListLength - 2; i++)
        {
            sb.append(l.get(i));
            sb.append("\n");
        }
        sb.append(l.get(ingredientsListLength - 1));

        return sb.toString();
    }

    private String buildAKAString(List<String> l){
        int akaListLength =  l.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < akaListLength - 2; i++)
        {
            sb.append(l.get(i));
            sb.append(" / ");
        }
        sb.append(l.get(akaListLength - 1));
        return sb.toString();
    }
}