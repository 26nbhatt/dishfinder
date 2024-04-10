package com.example.dishfinder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Credits extends AppCompatActivity {
    public Button returnButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits);
        returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
    }
}