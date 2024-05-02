// Package name following reverse-domain-name convention
package com.example.dishfinder;

// Import necessary classes for activity, UI components, and AI interaction
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// Import Google AI Generative AI client libraries
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
// Import additional Google libraries for UI components
import com.google.android.material.textfield.TextInputEditText;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

import java.util.concurrent.Executor;

public class Ingredients extends AppCompatActivity {

    // Declare buttons for recipe and search functionality
    public Button recipeButton2, searchButton2;

    // Declare input field for user to enter ingredients
    public TextInputEditText ingredients;

    // Declare text view to display generated dish
    public TextView dish;

    // Initialize Generative AI model
    // source: https://ai.google.dev/gemini-api/docs/get-started/android#java_1
    GenerativeModelFutures model = GenerativeModelFutures.from(new GenerativeModel("gemini-pro", "AIzaSyBE227sWGV6DVxTKaIVYraxt3vjZvhZq6U"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients);

        // Find UI elements by their IDs in the layout
        recipeButton2 = findViewById(R.id.recipeButton2);
        searchButton2 = findViewById(R.id.searchButton2);
        ingredients = findViewById(R.id.ingredientsInput);
        dish = findViewById(R.id.generatedDish);

        // Create a thread executor
        Executor executor = Runnable::run;

        // Set on-click listener for search button
        searchButton2.setOnClickListener(v -> {
            // Build content for AI request
            Content content = new Content.Builder()
                    .addText("Provide only the name of a dish containing the following ingredients: " + ingredients.getText())
                    .build();

            // Send AI request to generate dish name
            Futures.addCallback(model.generateContent(content), new FutureCallback<GenerateContentResponse>() {
                @Override
                public void onSuccess(GenerateContentResponse result) {
                    dish.setText(result.getText()); // Update dish text view with response
                }

                @Override
                public void onFailure(Throwable throwable) {
                    throwable.printStackTrace();  // Log errors for debugging
                }
            }, executor);
        });

        // Set on-click listener for recipe button navigating to recipe view
        recipeButton2.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(ingredients.getText())) {
                Intent intent = new Intent(this, Recipe.class);
                intent.putExtra("dish", dish.getText().toString()).putExtra("ingredients", ingredients.getText().toString());
                startActivity(intent);
            }
        });
    }
}
