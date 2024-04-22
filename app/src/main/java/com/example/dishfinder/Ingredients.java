package com.example.dishfinder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

import java.util.concurrent.Executor;

public class Ingredients extends AppCompatActivity {
    public Button recipeButton2, searchButton2;
    public TextInputEditText ingredients;
    public TextView dish;
    GenerativeModelFutures model = GenerativeModelFutures.from(new GenerativeModel("gemini-pro", "AIzaSyBE227sWGV6DVxTKaIVYraxt3vjZvhZq6U"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients);
        recipeButton2 = findViewById(R.id.recipeButton2);
        searchButton2 = findViewById(R.id.searchButton2);
        ingredients = findViewById(R.id.ingredientsInput);
        dish = findViewById(R.id.generatedDish);
        Executor executor = Runnable::run;
        searchButton2.setOnClickListener(v -> {
            Content content = new Content.Builder().addText("Provide only the name of a dish containing the following ingredients: " + ingredients.getText()).build();
            Futures.addCallback(model.generateContent(content), new FutureCallback<GenerateContentResponse>() {
                @Override
                public void onSuccess(GenerateContentResponse result) {
                    dish.setText(result.getText());
                }
                @Override
                public void onFailure(Throwable throwable) {
                    throwable.printStackTrace();
                }
            }, executor);
        });
        recipeButton2.setOnClickListener(v -> startActivity(new Intent(this, Recipe.class)));
    }
}