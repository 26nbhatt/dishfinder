package com.example.dishfinder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PreviousFoods extends AppCompatActivity {
    public Button homeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previous_foods);
        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
    }
}