
package com.example.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editQuantity;
    private EditText editUnit;
    private EditText editExpiry;

    private DatabaseHelper databaseHelper;
    private int ingredientId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        editName = findViewById(R.id.editIngredientName);
        editQuantity = findViewById(R.id.editIngredientQuantity);
        editUnit = findViewById(R.id.editIngredientUnit);
        editExpiry = findViewById(R.id.editIngredientExpiry);

        Button btnSave = findViewById(R.id.btnSaveIngredient);

        databaseHelper = new DatabaseHelper(this);

        ingredientId = getIntent().getIntExtra("ingredient_id", -1);

        if (ingredientId != -1) {
            loadIngredient();
            btnSave.setText("Update Ingredient");
        } else {
            btnSave.setText("Save Ingredient");
        }

        btnSave.setOnClickListener(v -> saveIngredient());
    }

    private void loadIngredient() {
        Cursor cursor = databaseHelper.getIngredientById(ingredientId);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COLUMN_NAME));

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COLUMN_QUANTITY));

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COLUMN_UNIT));

                String expiry = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COLUMN_EXPIRY));

                editName.setText(name);
                editQuantity.setText(String.valueOf(quantity));
                editUnit.setText(unit);
                editExpiry.setText(expiry == null ? "" : expiry);
            }

            cursor.close();
        }
    }

    private void saveIngredient() {
        String name = editName.getText().toString().trim();
        String quantityText = editQuantity.getText().toString().trim();
        String unit = editUnit.getText().toString().trim();
        String expiry = editExpiry.getText().toString().trim();

        if (name.isEmpty()) {
            editName.setError("Enter an ingredient name");
            editName.requestFocus();
            return;
        }

        if (quantityText.isEmpty()) {
            editQuantity.setError("Enter a quantity");
            editQuantity.requestFocus();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            editQuantity.setError("Enter a valid number");
            editQuantity.requestFocus();
            return;
        }

        if (quantity <= 0) {
            editQuantity.setError("Quantity must be greater than zero");
            editQuantity.requestFocus();
            return;
        }

        if (unit.isEmpty()) {
            editUnit.setError("Enter a unit, e.g. kg or pieces");
            editUnit.requestFocus();
            return;
        }

        boolean saved;

        if (ingredientId == -1) {
            saved = databaseHelper.addIngredient(
                    name, quantity, unit, expiry);
        } else {
            saved = databaseHelper.updateIngredient(
                    ingredientId, name, quantity, unit, expiry);
        }

        if (saved) {
            String message = ingredientId == -1
                    ? "Ingredient saved!"
                    : "Ingredient updated!";

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(
                    this,
                    "Could not save the ingredient.",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
