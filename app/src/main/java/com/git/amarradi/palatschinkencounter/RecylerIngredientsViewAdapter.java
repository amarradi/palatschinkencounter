package com.git.amarradi.palatschinkencounter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.git.amarradi.palatschinkencounter.models.IngredientsModel;

import java.util.ArrayList;

public class RecylerIngredientsViewAdapter extends RecyclerView.Adapter<RecylerIngredientsViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<IngredientsModel> ingredientsModels;

    public RecylerIngredientsViewAdapter(Context context, ArrayList<IngredientsModel> ingredientsModels) {
        this.context = context;
        this.ingredientsModels = ingredientsModels;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView CVtextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            CVtextView = itemView.findViewById(R.id.CVtextView);
        }
    }

    @NonNull
    @Override
    public RecylerIngredientsViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecylerIngredientsViewAdapter.MyViewHolder holder, int position) {

        holder.CVtextView.setText(ingredientsModels.get(position).getPreparationLine());

    }

    @Override
    public int getItemCount() {
        Log.d("getItemCount", String.valueOf(ingredientsModels.size()) );
        return ingredientsModels.size();
    }


}