package com.git.amarradi.palatschinkencounter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.git.amarradi.palatschinkencounter.models.PreparationModel;

import java.util.ArrayList;

public class RecylerPreparationViewAdapter extends RecyclerView.Adapter<RecylerPreparationViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<PreparationModel> preparationModels;

    public RecylerPreparationViewAdapter(Context context,
                                         ArrayList<PreparationModel> preparationModels) {
        this.context = context;
        this.preparationModels = preparationModels;

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
    public RecylerPreparationViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecylerPreparationViewAdapter.MyViewHolder holder, int position) {

        holder.CVtextView.setText(preparationModels.get(position).getPreparationLine());

    }

    @Override
    public int getItemCount() {
        Log.d("getItemCount", String.valueOf(preparationModels.size()) );
        return preparationModels.size();
    }


}