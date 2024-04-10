package com.example.dishfinder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Ingredients extends AppCompatActivity {
    public Button recipeButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients);
        recipeButton2 = findViewById(R.id.recipeButton2);
        recipeButton2.setOnClickListener(v -> startActivity(new Intent(this, Recipe.class)));
    }
}