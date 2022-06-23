package com.git.amarradi.palatschinkencounter;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeActivity extends AppCompatActivity {
    ListView listView_Ingredients;

    String[] listItem_ingredients;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
       listView_Ingredients = findViewById(R.id.listViewIngredients);
       listItem_ingredients = getResources().getStringArray(R.array.ingredients_array);

       

       final ArrayAdapter<String> arrayAdapter_Ingredients = new ArrayAdapter<>(this,
                R.layout.simple_list_item_1, listItem_ingredients);
       listView_Ingredients.setAdapter(arrayAdapter_Ingredients);

    }
}
