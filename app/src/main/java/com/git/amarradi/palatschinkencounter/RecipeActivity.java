package com.git.amarradi.palatschinkencounter;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.git.amarradi.palatschinkencounter.models.IngredientsModel;
import com.git.amarradi.palatschinkencounter.models.PreparationModel;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    ArrayList<IngredientsModel> ingredientsModels = new ArrayList<>();
    ArrayList<PreparationModel> preparationModels = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        RecyclerView recyclerView_Ingredients = findViewById(R.id.recyclerView_ingredients);

        setupIngredientsModels();

        RecylerIngredientsViewAdapter recylerIngredientsViewAdapter =
                new RecylerIngredientsViewAdapter(this,ingredientsModels);
        recyclerView_Ingredients.setAdapter(recylerIngredientsViewAdapter);
        recyclerView_Ingredients.setLayoutManager(new LinearLayoutManager(this));


        RecyclerView recyclerView_Preparation = findViewById(R.id.RecyclerView_preparation);

        setupPreparationModels();
        RecylerPreparationViewAdapter recylerPreparationViewAdapter =
                new RecylerPreparationViewAdapter(this,preparationModels);
        recyclerView_Preparation.setNestedScrollingEnabled(false);
        recyclerView_Preparation.setAdapter(recylerPreparationViewAdapter);
        recyclerView_Preparation.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupPreparationModels() {
        String[] strings_preparation = getResources().getStringArray(R.array.preparation_array);
        for (String s : strings_preparation) {
            preparationModels.add(new PreparationModel(s));
        }
    }

    public void setupIngredientsModels(){
        String[] strings_ingredients = getResources().getStringArray(R.array.ingredients_array);
        for (String strings_ingredient : strings_ingredients) {
            ingredientsModels.add(new IngredientsModel(strings_ingredient));
        }
    }

}