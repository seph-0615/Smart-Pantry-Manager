package com.example.smartpantrymanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IngredientAdapter
        extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private final Context context;
    private final ArrayList<Ingredient> ingredients;
    private final DatabaseHelper databaseHelper;

    public IngredientAdapter(Context context,
                             ArrayList<Ingredient> ingredients,
                             DatabaseHelper databaseHelper) {
        this.context = context;
        this.ingredients = ingredients;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull IngredientViewHolder holder, int position) {

        Ingredient ingredient = ingredients.get(position);

        holder.txtName.setText(ingredient.getName());
        holder.txtQuantity.setText(
                "Quantity: " + ingredient.getQuantity()
                        + " " + ingredient.getUnit());

        String expiry = ingredient.getExpiry();
        holder.txtExpiry.setText(
                expiry == null || expiry.trim().isEmpty()
                        ? "Expiry: Not specified"
                        : "Expiry: " + expiry);

        holder.btnEdit.setOnClickListener(view -> {
            int currentPosition = holder.getBindingAdapterPosition();
            if (currentPosition == RecyclerView.NO_POSITION) return;

            Ingredient selected = ingredients.get(currentPosition);
            Intent intent = new Intent(context, AddEditIngredientActivity.class);
            intent.putExtra("ingredient_id", selected.getId());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(view -> {
            int currentPosition = holder.getBindingAdapterPosition();
            if (currentPosition == RecyclerView.NO_POSITION) return;

            Ingredient selected = ingredients.get(currentPosition);

            new AlertDialog.Builder(context)
                    .setTitle("Delete ingredient")
                    .setMessage("Are you sure you want to delete "
                            + selected.getName() + "?")
                    .setNegativeButton("Cancel", (dialog, which) ->
                            dialog.dismiss())
                    .setPositiveButton("Delete", (dialog, which) -> {
                        int deleted = databaseHelper.deleteIngredient(
                                selected.getId());

                        if (deleted > 0) {
                            int index = ingredients.indexOf(selected);
                            if (index >= 0) {
                                ingredients.remove(index);
                                notifyItemRemoved(index);
                            }

                            Toast.makeText(context,
                                    "Ingredient deleted successfully",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context,
                                    "Could not delete ingredient",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtQuantity;
        TextView txtExpiry;
        Button btnEdit;
        Button btnDelete;

        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtIngredientName);
            txtQuantity = itemView.findViewById(R.id.txtIngredientQuantity);
            txtExpiry = itemView.findViewById(R.id.txtIngredientExpiry);
            btnEdit = itemView.findViewById(R.id.btnEditIngredient);
            btnDelete = itemView.findViewById(R.id.btnDeleteIngredient);
        }
    }
}
