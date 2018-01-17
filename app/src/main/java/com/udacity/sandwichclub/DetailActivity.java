package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    Sandwich sandwich;

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
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        TextView sandwichName = (TextView) findViewById(R.id.name);
        if (sandwich.getMainName().isEmpty())
            sandwichName.setText(R.string.na);
        else
            sandwichName.setText(sandwich.getMainName());

        TextView alias = (TextView) findViewById(R.id.also_known_as);
        StringBuilder aliasNames = new StringBuilder("");
        if (sandwich.getAlsoKnownAs().size() == 0)
            aliasNames.append(getString(R.string.na));
        else
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++)
                aliasNames.append(sandwich.getAlsoKnownAs().get(i) + " ");
        alias.setText(aliasNames);

        TextView ingredients = (TextView) findViewById(R.id.ingredients);
        StringBuilder ingredient = new StringBuilder("");
        if (sandwich.getIngredients().size() == 0)
            ingredient.append(getString(R.string.na));
        else
            for (int i = 0; i < sandwich.getIngredients().size(); i++)
                ingredient.append(sandwich.getIngredients().get(i) + " ");
        ingredients.setText(ingredient);

        TextView place = (TextView) findViewById(R.id.origin);
        if (sandwich.getPlaceOfOrigin().isEmpty())
            place.setText(getString(R.string.na));
        else
            place.setText(sandwich.getPlaceOfOrigin());

        TextView description = (TextView) findViewById(R.id.description);
        if (sandwich.getDescription().isEmpty())
            description.setText(getString(R.string.na));
        else
            description.setText(sandwich.getDescription());
    }
}
