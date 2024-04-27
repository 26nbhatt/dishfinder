// Package name following reverse-domain-name convention
package com.example.dishfinder;

// Import necessary classes for activity and UI components
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

// Class declaration for the main activity 
public class MainActivity extends AppCompatActivity {

    // Declare buttons for different functionalities
    public Button dishButton, ingredientsButton, creditsButton, previousFoodsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find buttons by their IDs in the layout
        dishButton = findViewById(R.id.dishButton);
        ingredientsButton = findViewById(R.id.ingredientsButton);
        creditsButton = findViewById(R.id.creditsButton);
        previousFoodsButton = findViewById(R.id.previousFoodsButton);

        // Set on-click listeners for each button
        dishButton.setOnClickListener(v -> startActivity(new Intent(this, Dish.class)));
        ingredientsButton.setOnClickListener(v -> startActivity(new Intent(this, Ingredients.class)));
        creditsButton.setOnClickListener(v -> startActivity(new Intent(this, Credits.class)));
        previousFoodsButton.setOnClickListener(v -> startActivity(new Intent(this, PreviousFoods.class)));
    }
}