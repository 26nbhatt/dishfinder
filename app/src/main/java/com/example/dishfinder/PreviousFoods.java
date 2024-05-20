package com.example.dishfinder;

import static com.example.dishfinder.Recipe.getSavedList;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PreviousFoods extends AppCompatActivity {
    public RecyclerView recyclerView;
    public Button homeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previous_foods);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new RecyclerAdapter(Recipe.getSavedList(this), PreviousFoods.this));
        recyclerView.setLayoutManager(new LinearLayoutManager(PreviousFoods.this));
        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
    }
}