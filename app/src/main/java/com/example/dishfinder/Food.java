package com.example.dishfinder;

import java.io.Serializable;

public class Food implements Serializable {
    private String name, ingredients, recipe;
    public Food (String name, String ingredients, String recipe) {
        this.name = name;
        this.ingredients = ingredients;
        this.recipe = recipe;
    }
    public String getName() {
        return name;
    }
    public String getIngredients() {
        return ingredients;
    }
    public String getRecipe() {
        return recipe;
    }
}
