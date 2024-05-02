// Package name following reverse-domain-name convention
package com.example.dishfinder;

// Import necessary classes for activity, UI components, and AI interaction
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
// TextInputEditText for user text input
import android.widget.SeekBar;
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

public class Dish extends AppCompatActivity {

    // Declare buttons for recipe and search functionality
    public Button recipeButton1, searchButton1;

    // Declare input field for user to enter dish name
    public TextInputEditText dish;

    // Declare text views to display ingredients and cost
    public TextView ingredients, costView;

    // Declare seek bar for user to specify budget
    public SeekBar costBar;

    // Initialize Generative AI model
    // source: https://ai.google.dev/gemini-api/docs/get-started/android#java_1
    GenerativeModelFutures model = GenerativeModelFutures.from(new GenerativeModel("gemini-pro", "AIzaSyBE227sWGV6DVxTKaIVYraxt3vjZvhZq6U"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dish);

        // Find UI elements by their IDs in the layout
        recipeButton1 = findViewById(R.id.recipeButton1);
        searchButton1 = findViewById(R.id.searchButton1);
        dish = findViewById(R.id.dishInput);
        ingredients = findViewById(R.id.generatedIngredients);
        costView = findViewById(R.id.costView);
        costBar = findViewById(R.id.costBar);

        // Create a thread executor
        Executor executor = Runnable::run;

        // Set on-click listener for search button
        searchButton1.setOnClickListener(v -> {
            // Build content for AI request
            Content content = new Content.Builder()
                    .addText("Provide the ingredients separated by commas for the following dish with a budget of " + costBar.getProgress() + " dollars: " + dish.getText() + ". Even if the dish appears to be unreasonable at the given price, incorporate expensive ingredients. Once you have compiled a list of ingredients, separate each item with a comma and space without the price of the individual ingredients.")
                    .build();

            // Send Google Gemini request to generate ingredients list
            Futures.addCallback(model.generateContent(content), new FutureCallback<GenerateContentResponse>() {
                @Override
                public void onSuccess(GenerateContentResponse result) {
                    ingredients.setText(result.getText()); // Update ingredients text view with response
                }

                @Override
                public void onFailure(Throwable throwable) {
                    throwable.printStackTrace();  // Log errors for debugging
                }
            }, executor);
        });

        // Set on-change listener for cost seek bar
        costBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String text = "Cost: $" + progress; // Update cost display based on progress
                costView.setText(text);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Set on-click listener for recipe button navigating to recipe view
        recipeButton1.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(dish.getText())) {
                Intent intent = new Intent(this, Recipe.class);
                intent.putExtra("dish", dish.getText().toString()).putExtra("ingredients", ingredients.getText());
                startActivity(intent);
            }
        });
    }
}
