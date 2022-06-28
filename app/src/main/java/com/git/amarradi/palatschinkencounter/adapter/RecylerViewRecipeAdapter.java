package com.git.amarradi.palatschinkencounter.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.git.amarradi.palatschinkencounter.R;
import com.git.amarradi.palatschinkencounter.models.RecipeModel;

import java.util.ArrayList;

public class RecylerViewRecipeAdapter extends RecyclerView.Adapter<RecylerViewRecipeAdapter.MyViewHolder> {

    Context context;
    ArrayList<RecipeModel> recipeModels;

    public RecylerViewRecipeAdapter(Context context, ArrayList<RecipeModel> recipeModels) {
        this.context = context;
        this.recipeModels = recipeModels;

    }

    @NonNull
    @Override
    public RecylerViewRecipeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecylerViewRecipeAdapter.MyViewHolder holder, int position) {

        holder.CVtextView.setText(recipeModels.get(position).getRecipeLine());

    }

    @Override
    public int getItemCount() {
        Log.d("getItemCount", String.valueOf(recipeModels.size()));
        return recipeModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView CVtextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            CVtextView = itemView.findViewById(R.id.CVtextView);
        }
    }


}