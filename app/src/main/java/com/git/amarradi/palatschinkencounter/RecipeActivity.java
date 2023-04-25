package com.git.amarradi.palatschinkencounter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.git.amarradi.palatschinkencounter.adapter.RecylerViewRecipeAdapter;
import com.git.amarradi.palatschinkencounter.models.RecipeModel;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    ArrayList<RecipeModel> ingredientsModels = new ArrayList<>();
    ArrayList<RecipeModel> preparationModels = new ArrayList<>();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.Theme_Palatschinkencounter);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        RecyclerView recyclerView_Ingredients = findViewById(R.id.recyclerView_ingredients);

        setupRecipeModels();

        RecylerViewRecipeAdapter recylerViewIngredientsAdapter =
                new RecylerViewRecipeAdapter(this, ingredientsModels);
        recyclerView_Ingredients.setAdapter(recylerViewIngredientsAdapter);
        recyclerView_Ingredients.setLayoutManager(new LinearLayoutManager(this));


        RecyclerView recyclerView_Preparation = findViewById(R.id.RecyclerView_preparation);

        setupPreparationModels();
        RecylerViewRecipeAdapter recylerViewPreparationAdapter =
                new RecylerViewRecipeAdapter(this, preparationModels);
        recyclerView_Preparation.setNestedScrollingEnabled(false);
        recyclerView_Preparation.setAdapter(recylerViewPreparationAdapter);
        recyclerView_Preparation.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_setting) {
            Intent intentSetting = new Intent(this, SettingActivity.class);
            startActivity(intentSetting);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupPreparationModels() {
        String[] strings_preparation = getResources().getStringArray(R.array.preparation_array);
        for (String s : strings_preparation) {
            preparationModels.add(new RecipeModel(s));
        }
    }

    public void setupRecipeModels() {
        String[] strings_ingredients = getResources().getStringArray(R.array.ingredients_array);
        for (String strings_ingredient : strings_ingredients) {
            ingredientsModels.add(new RecipeModel(strings_ingredient));
        }
    }

}