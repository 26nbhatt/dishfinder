package com.example.dishfinder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
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

public class Dish extends AppCompatActivity {
    public Button recipeButton1, searchButton1;
    public TextInputEditText dish;
    public TextView ingredients, costView;
    public SeekBar costBar;
    GenerativeModelFutures model = GenerativeModelFutures.from(new GenerativeModel("gemini-pro", "AIzaSyBE227sWGV6DVxTKaIVYraxt3vjZvhZq6U"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dish);
        recipeButton1 = findViewById(R.id.recipeButton1);
        searchButton1 = findViewById(R.id.searchButton1);
        dish = findViewById(R.id.dishInput);
        ingredients = findViewById(R.id.generatedIngredients);
        costView = findViewById(R.id.costView);
        costBar = findViewById(R.id.costBar);
        Executor executor = Runnable::run;
        searchButton1.setOnClickListener(v -> {
            Content content = new Content.Builder().addText("Provide the ingredients separated by commas for the following dish with a budget of " + costBar.getProgress() + " dollars: " + dish.getText() + ". Even if the dish appears to be unreasonable at the given price, incorporate expensive ingredients.").build();
            Futures.addCallback(model.generateContent(content), new FutureCallback<GenerateContentResponse>() {
                @Override
                public void onSuccess(GenerateContentResponse result) {
                    ingredients.setText(result.getText());
                }
                @Override
                public void onFailure(Throwable throwable) {
                    throwable.printStackTrace();
                }
            }, executor);
        });
        costBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String text = "Cost: $" + i;
                costView.setText(text);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        recipeButton1.setOnClickListener(v -> startActivity(new Intent(this, Recipe.class)));
    }
}
