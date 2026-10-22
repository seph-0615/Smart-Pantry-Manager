
package com.example.smartpantrymanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private final Context context;
    private final Cursor cursor;
    private final DatabaseHelper databaseHelper;

    public IngredientAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_ingredient, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull IngredientViewHolder holder, int position) {

        if (cursor == null || !cursor.moveToPosition(position)) {
            return;
        }

        int id = cursor.getInt(
                cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));

        String name = cursor.getString(
                cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));

        double quantity = cursor.getDouble(
                cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_QUANTITY));

        String unit = cursor.getString(
                cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_UNIT));

        String expiry = cursor.getString(
                cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXPIRY));

        holder.txtIngredientName.setText(name);

        holder.txtIngredientQuantity.setText(
                "Quantity: " + quantity + " " + unit);

        if (expiry == null || expiry.trim().isEmpty()) {
            holder.txtIngredientExpiry.setText(
                    "Expiry date: Not specified");
        } else {
            holder.txtIngredientExpiry.setText("Expiry date: " + expiry);
        }

        holder.btnEditIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(
                    context,
                    AddEditIngredientActivity.class
            );

            intent.putExtra("ingredient_id", id);
            context.startActivity(intent);
        });

        holder.btnDeleteIngredient.setOnClickListener(v ->
                new AlertDialog.Builder(context)
                        .setTitle("Delete ingredient?")
                        .setMessage("Are you sure you want to delete " + name + "?")
                        .setNegativeButton("Cancel", (dialog, which) ->
                                dialog.dismiss())
                        .setPositiveButton("Delete", (dialog, which) -> {
                            boolean deleted =
                                    databaseHelper.deleteIngredient(id);

                            if (deleted) {
                                Toast.makeText(
                                        context,
                                        name + " deleted",
                                        Toast.LENGTH_SHORT
                                ).show();

                                if (context instanceof PantryActivity) {
                                    ((PantryActivity) context).refreshPantry();
                                }
                            } else {
                                Toast.makeText(
                                        context,
                                        "Could not delete ingredient",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        })
                        .show()
        );
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView txtIngredientName;
        TextView txtIngredientQuantity;
        TextView txtIngredientExpiry;
        Button btnEditIngredient;
        Button btnDeleteIngredient;

        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            txtIngredientName =
                    itemView.findViewById(R.id.txtIngredientName);

            txtIngredientQuantity =
                    itemView.findViewById(R.id.txtIngredientQuantity);

            txtIngredientExpiry =
                    itemView.findViewById(R.id.txtIngredientExpiry);

            btnEditIngredient =
                    itemView.findViewById(R.id.btnEditIngredient);

            btnDeleteIngredient =
                    itemView.findViewById(R.id.btnDeleteIngredient);
        }
    }
}
