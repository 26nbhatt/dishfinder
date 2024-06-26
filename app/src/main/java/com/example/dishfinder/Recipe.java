// Package declaration
package com.example.dishfinder;

// Import necessary libraries
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.Executor;

// Class definition for the Recipe activity
public class Recipe extends AppCompatActivity {

    // Declare member variables for UI elements
    public Button updateButton;
    public TextView dishName, generatedRecipe;

    public ImageView returnView4;

    // Initialize Generative AI model
    // source: https://ai.google.dev/gemini-api/docs/get-started/android#java_1
    GenerativeModelFutures model = GenerativeModelFutures.from(new GenerativeModel("gemini-pro", "AIzaSyBE227sWGV6DVxTKaIVYraxt3vjZvhZq6U"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe); // Set the layout for this activity

        // Find UI elements by their IDs in the layout
        updateButton = findViewById(R.id.updateButton);
        dishName = findViewById(R.id.dishName);
        generatedRecipe = findViewById(R.id.generatedRecipe);
        returnView4 = findViewById(R.id.returnView4);

        // Get the dish name and ingredients from the previous activity
        Intent intent = getIntent();
        dishName.setText(intent.getStringExtra("dish"));

        // Define an Executor
        Executor executor = Runnable::run;

        // Build the content object for the generative AI model
        Content content = new Content.Builder()
                .addText("Provide a recipe for " + intent.getStringExtra("dish") + " containing the following ingredients: " + intent.getStringExtra("ingredients") + ". Do not include the name and ingredients - only the recipe in numerical order.")
                .build();

        // Asynchronously generate recipe using the model
        Futures.addCallback(model.generateContent(content), new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                generatedRecipe.setText(result.getText()); // Update the recipe text view with the generated recipe
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();  // Handle potential errors during recipe generation
            }
        }, executor);

        // Set on-click listener for update button navigating to previous foods
        updateButton.setOnClickListener(v -> {
            appendDish(this, new Food(intent.getStringExtra("dish"), intent.getStringExtra("ingredients"), generatedRecipe.getText().toString()));
            startActivity(new Intent(this, PreviousFoods.class));
        });
        returnView4.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
    }
    public static ArrayList<Food> getSavedList(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PREFS_DISH", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("DISH_LIST", null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Food>>() {}.getType();
        ArrayList<Food> dishList = gson.fromJson(json, type);
        if (dishList == null) dishList = new ArrayList<>();
        return dishList;
    }
    public static void appendDish(Context context, Food dish) {
        ArrayList<Food> playersList = getSavedList(context);
        playersList.add(dish);
        SharedPreferences sharedPreferences = context.getSharedPreferences("PREFS_DISH", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(playersList);
        editor.putString("DISH_LIST", json);
        editor.apply();
    }
}