package com.git.amarradi.palatschinkencounter;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        TextView recipe = findViewById(R.id.tv_recipe_txt);
        TextView ingredients = findViewById(R.id.pancake_ingredients);
        TextView flour = findViewById(R.id.tv_flour);
        TextView eggs = findViewById(R.id.tv_eggs);
        TextView milk = findViewById(R.id.tv_milk);
        TextView mineralWater = findViewById(R.id.tv_mineral_water);
        TextView salt = findViewById(R.id.tv_salt);
        TextView preparation = findViewById(R.id.pancake_preparation);
        TextView deliuteEggs = findViewById(R.id.tv_dilute_eggs);
        TextView addFlour = findViewById(R.id.tv_add_flour);
        TextView addMilk = findViewById(R.id.tv_add_oil);
        TextView addOil = findViewById(R.id.tv_add_mineral_water);

        Typeface typeface = getResources().getFont(R.font.opensans_regular);

        recipe.setTypeface(typeface);
        ingredients.setTypeface(typeface);
        flour.setTypeface(typeface);
        eggs.setTypeface(typeface);
        milk.setTypeface(typeface);
        mineralWater.setTypeface(typeface);
        salt.setTypeface(typeface);
        preparation.setTypeface(typeface);
        deliuteEggs.setTypeface(typeface);
        addFlour.setTypeface(typeface);
        addMilk.setTypeface(typeface);
        addOil.setTypeface(typeface);
    }
}
