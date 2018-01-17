package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String LOG_TAG = JsonUtils.class.getSimpleName();
    private final static String SANDWICH_NAME = "name";
    private final static String SANDWICH_MAIN_NAME = "mainName";
    private final static String SANDWICH_ALSO_KNOWN_AS = "alsoKnownAs";
    private final static String SANDWICH_PLACE_OF_ORIGIN = "placeOfOrigin";
    private final static String SANDWICH_DESCRIPTION = "description";
    private final static String SANDWICH_IMAGE = "image";
    private final static String SANDWICH_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;

        try {
            List<String> alias = new ArrayList<>();
            List<String> ingredients = new ArrayList<>();
            JSONObject rootObject = new JSONObject(json);
            JSONObject name = rootObject.getJSONObject(SANDWICH_NAME);
            String sandwichName = name.optString(SANDWICH_MAIN_NAME);
            JSONArray alsoKnowsAs = name.optJSONArray(SANDWICH_ALSO_KNOWN_AS);
            for (int i = 0; i < alsoKnowsAs.length(); i++)
                alias.add(alsoKnowsAs.optString(i));
            String placeOfOrigin = rootObject.optString(SANDWICH_PLACE_OF_ORIGIN);
            String description = rootObject.optString(SANDWICH_DESCRIPTION);
            String image = rootObject.optString(SANDWICH_IMAGE);
            JSONArray ingredientsArray = rootObject.optJSONArray(SANDWICH_INGREDIENTS);
            for (int i = 0; i < ingredientsArray.length(); i++)
                ingredients.add(ingredientsArray.optString(i));
            sandwich = new Sandwich(sandwichName, alias, placeOfOrigin, description, image, ingredients);
            Log.e(LOG_TAG, "Successfully  JSON parsing");
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error in JSON parsing", e);
        }
        return sandwich;
    }
}
