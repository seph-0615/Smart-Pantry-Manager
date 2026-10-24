
package com.example.smartpantrymanager;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editQuantity;
    private EditText editUnit;
    private EditText editExpiry;

    private DatabaseHelper databaseHelper;
    private int ingredientId = -1;

    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd", Locale.US);

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

        dateFormat.setLenient(false);

        ingredientId = getIntent().getIntExtra("ingredient_id", -1);

        editExpiry.setOnClickListener(v -> showExpiryDatePicker());

        if (ingredientId != -1) {
            loadIngredient();
            btnSave.setText("Update Ingredient");
        } else {
            btnSave.setText("Save Ingredient");
        }

        btnSave.setOnClickListener(v -> saveIngredient());
    }

    private void showExpiryDatePicker() {
        Calendar calendar = Calendar.getInstance();

        String currentDate = editExpiry.getText().toString().trim();

        if (!currentDate.isEmpty()) {
            try {
                Date date = dateFormat.parse(currentDate);

                if (date != null) {
                    calendar.setTime(date);
                }
            } catch (ParseException ignored) {
                // Use today's date if the existing value is invalid.
            }
        }

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String selectedDate = String.format(
                            Locale.US,
                            "%04d-%02d-%02d",
                            year,
                            month + 1,
                            dayOfMonth
                    );

                    editExpiry.setText(selectedDate);
                    editExpiry.setError(null);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.setButton(
                DatePickerDialog.BUTTON_NEUTRAL,
                "Clear date",
                (dialogInterface, which) -> {
                    editExpiry.setText("");
                    editExpiry.setError(null);
                }
        );

        dialog.show();
    }

    private void loadIngredient() {
        Cursor cursor = databaseHelper.getIngredientById(ingredientId);

        if (cursor == null) {
            Toast.makeText(
                    this,
                    "Could not load ingredient.",
                    Toast.LENGTH_LONG
            ).show();
            finish();
            return;
        }

        try {
            if (cursor.moveToFirst()) {
                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COLUMN_NAME
                        )
                );

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COLUMN_QUANTITY
                        )
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COLUMN_UNIT
                        )
                );

                String expiry = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COLUMN_EXPIRY
                        )
                );

                editName.setText(name);
                editQuantity.setText(String.valueOf(quantity));
                editUnit.setText(unit);
                editExpiry.setText(expiry == null ? "" : expiry);

            } else {
                Toast.makeText(
                        this,
                        "Ingredient not found.",
                        Toast.LENGTH_SHORT
                ).show();
                finish();
            }

        } catch (IllegalArgumentException e) {
            Toast.makeText(
                    this,
                    "Could not read ingredient details.",
                    Toast.LENGTH_LONG
            ).show();
            finish();

        } finally {
            cursor.close();
        }
    }

    private void saveIngredient() {
        String name = editName.getText().toString().trim();
        String quantityText =
                editQuantity.getText().toString().trim();
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

        if (Double.isNaN(quantity)
                || Double.isInfinite(quantity)
                || quantity <= 0) {
            editQuantity.setError(
                    "Quantity must be greater than zero"
            );
            editQuantity.requestFocus();
            return;
        }

        if (unit.isEmpty()) {
            editUnit.setError("Enter a unit, e.g. kg or pieces");
            editUnit.requestFocus();
            return;
        }

        if (!expiry.isEmpty()) {
            try {
                Date parsedDate = dateFormat.parse(expiry);

                if (parsedDate == null
                        || !dateFormat.format(parsedDate).equals(expiry)) {
                    throw new ParseException("Invalid date", 0);
                }

            } catch (ParseException e) {
                editExpiry.setError(
                        "Choose a valid expiry date"
                );
                editExpiry.requestFocus();
                return;
            }
        }

        boolean saved;

        if (ingredientId == -1) {
            saved = databaseHelper.addIngredient(
                    name,
                    quantity,
                    unit,
                    expiry
            );
        } else {
            saved = databaseHelper.updateIngredient(
                    ingredientId,
                    name,
                    quantity,
                    unit,
                    expiry
            ) > 0;
        }

        if (saved) {
            String message = ingredientId == -1
                    ? "Ingredient saved!"
                    : "Ingredient updated!";

            Toast.makeText(
                    this,
                    message,
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {
            Toast.makeText(
                    this,
                    "Could not save the ingredient.",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @Override
    protected void onDestroy() {
        if (databaseHelper != null) {
            databaseHelper.close();
        }

        super.onDestroy();
    }
}
