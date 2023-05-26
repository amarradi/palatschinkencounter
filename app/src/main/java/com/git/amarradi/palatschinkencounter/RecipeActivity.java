package com.git.amarradi.palatschinkencounter;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.git.amarradi.palatschinkencounter.adapter.RecylerViewRecipeAdapter;
import com.git.amarradi.palatschinkencounter.models.RecipeModel;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity implements NotificationDialog.NotificationDialogListener{

    ArrayList<RecipeModel> ingredientsModels = new ArrayList<>();
    ArrayList<RecipeModel> preparationModels = new ArrayList<>();
    private static final int SCREEN_ORIENTATION_UNSPECIFIED = SCREEN_ORIENTATION_PORTRAIT;

    private int portions = 1;

    @SuppressLint({"ResourceAsColor", "StringFormatInvalid", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.Theme_Palatschinkencounter);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        setRequestedOrientation(SCREEN_ORIENTATION_UNSPECIFIED);
        TextView ingredients;
        ImageButton decrease;
        ImageButton increase;

        ingredients = findViewById(R.id.ingredients_tv);
        Typeface typeface_regular = getResources().getFont(R.font.opensans_regular);
        Typeface typeface_bold = getResources().getFont(R.font.opensans_bold);
        TextView recipe_head = findViewById(R.id.recipe_head_tv);
        recipe_head.setTypeface(typeface_bold);

        ingredients.setTypeface(typeface_regular);

        ingredients.setText(String.format(getResources().getString(R.string.pancake_ingredients), portions));

        RecyclerView recyclerView_Ingredients = findViewById(R.id.recyclerView_ingredients);

        setupRecipeModels(portions);

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

        increase = findViewById(R.id.increase);
        increase.setOnClickListener(v -> {
            portions++;
            ingredients.setText(String.format(getResources().getString(R.string.pancake_ingredients), portions));
            ingredientsModels.clear();
            recylerViewIngredientsAdapter.notifyDataSetChanged();
            setupRecipeModels(portions);
        });

        decrease = findViewById(R.id.decrease);
        decrease.setOnClickListener(v -> {
            if(portions>1){
                portions--;
                ingredients.setText(String.format(getResources().getString(R.string.pancake_ingredients), portions));
                ingredientsModels.clear();
                recylerViewIngredientsAdapter.notifyDataSetChanged();
                setupRecipeModels(portions);
            } else{
                ingredients.setText(String.format(getResources().getString(R.string.pancake_ingredients), portions));
                NotificationDialog notificationDialog = new NotificationDialog();
                notificationDialog.show(getSupportFragmentManager(),getResources().getString(R.string.notification));
            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_setting, menu);

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

    public void setupRecipeModels(int person) {
        Resources resources = getResources();
        int[] ingredients = resources.getIntArray(R.array.Ingredients);
        String[] strings_ingredients = resources.getStringArray(R.array.ingredients_array);
        int count = 0;
        for (String strings_ingredient : strings_ingredients) {
            String s = String.format(strings_ingredient, ingredients[count]*person);
            count++;
            ingredientsModels.add(new RecipeModel(s));
            }
    }

    private void setupPreparationModels() {
        String[] strings_preparation = getResources().getStringArray(R.array.preparation_array);
        for (String s : strings_preparation) {
            preparationModels.add(new RecipeModel(s));
        }
    }

    @Override
    public void onOKClicked() {

    }
}