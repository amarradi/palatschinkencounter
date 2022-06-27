package com.git.amarradi.palatschinkencounter.models;

public class IngredientsModel {
    private final String ingredientsLine;

    public IngredientsModel(String preparationLine) {
        this.ingredientsLine = preparationLine;
    }

    public String getPreparationLine() {
        return ingredientsLine;
    }
}
