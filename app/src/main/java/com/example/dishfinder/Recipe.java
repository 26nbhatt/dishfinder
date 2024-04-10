package com.example.dishfinder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Recipe extends AppCompatActivity {
    public Button updateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);
        updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(v -> startActivity(new Intent(this, PreviousFoods.class)));
    }
}