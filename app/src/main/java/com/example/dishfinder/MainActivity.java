package com.example.dishfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public Button dishButton, ingredientsButton, creditsButton, previousFoodsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dishButton = findViewById(R.id.dishButton);
        ingredientsButton = findViewById(R.id.ingredientsButton);
        creditsButton = findViewById(R.id.creditsButton);
        previousFoodsButton = findViewById(R.id.previousFoodsButton);
        dishButton.setOnClickListener(v -> startActivity(new Intent(this, Dish.class)));
        ingredientsButton.setOnClickListener(v -> startActivity(new Intent(this, Ingredients.class)));
        creditsButton.setOnClickListener(v -> startActivity(new Intent(this, Credits.class)));
    }
}