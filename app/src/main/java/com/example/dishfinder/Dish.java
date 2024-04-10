package com.example.dishfinder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Dish extends AppCompatActivity {
    public Button recipeButton1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dish);
        recipeButton1 = findViewById(R.id.recipeButton1);
        recipeButton1.setOnClickListener(v -> startActivity(new Intent(this, Recipe.class)));
    }
}
