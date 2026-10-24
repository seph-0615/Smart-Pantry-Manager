
package com.example.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PantryActivity extends AppCompatActivity {

    private static final double LOW_STOCK_THRESHOLD = 2.0;

    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerPantry;
    private TextView txtEmptyPantry;
    private TextView txtTotalIngredients;
    private TextView txtExpiringSoon;
    private TextView txtExpired;
    private TextView txtLowStockAlert;
    private EditText edtSearchIngredient;
    private Spinner spinnerIngredientFilter;
    private IngredientAdapter adapter;

    private final ArrayList<Ingredient> allIngredients = new ArrayList<>();
    private final ArrayList<Ingredient> ingredientList = new ArrayList<>();

    private final String[] filterOptions = {
            "All ingredients",
            "Low stock",
            "Expiring soon",
            "Expired"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        // Navigate back to the dashboard.
        Button btnBackHome = findViewById(R.id.btnBackHome);
        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(PantryActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        Button btnAddIngredient = findViewById(R.id.btnAddIngredient);

        recyclerPantry = findViewById(R.id.recyclerPantry);
        txtEmptyPantry = findViewById(R.id.txtEmptyPantry);
        txtTotalIngredients = findViewById(R.id.txtTotalIngredients);
        txtExpiringSoon = findViewById(R.id.txtExpiringSoon);
        txtExpired = findViewById(R.id.txtExpired);
        txtLowStockAlert = findViewById(R.id.txtLowStockAlert);
        edtSearchIngredient = findViewById(R.id.edtSearchIngredient);
        spinnerIngredientFilter = findViewById(R.id.spinnerIngredientFilter);

        databaseHelper = new DatabaseHelper(this);

        recyclerPantry.setLayoutManager(new LinearLayoutManager(this));

        adapter = new IngredientAdapter(
                this,
                ingredientList,
                databaseHelper
        );
        recyclerPantry.setAdapter(adapter);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                filterOptions
        );
        spinnerAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        spinnerIngredientFilter.setAdapter(spinnerAdapter);

        edtSearchIngredient.addTextChangedListener(
                new android.text.TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s, int start, int before, int count) {
                        applyFilters();
                    }

                    @Override
                    public void afterTextChanged(android.text.Editable s) {
                    }
                }
        );

        spinnerIngredientFilter.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {
                        applyFilters();
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                }
        );

        btnAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(
                    PantryActivity.this,
                    AddEditIngredientActivity.class
            );
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshPantry();
    }

    public void refreshPantry() {
        allIngredients.clear();

        int expiringCount = 0;
        int expiredCount = 0;
        int lowStockCount = 0;

        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        todayCalendar.set(Calendar.SECOND, 0);
        todayCalendar.set(Calendar.MILLISECOND, 0);

        Date today = todayCalendar.getTime();

        Calendar threeDaysCalendar = (Calendar) todayCalendar.clone();
        threeDaysCalendar.add(Calendar.DAY_OF_YEAR, 3);
        Date threeDaysFromNow = threeDaysCalendar.getTime();

        Cursor cursor = databaseHelper.getAllIngredients();

        if (cursor != null) {
            try {
                int idColumn = cursor.getColumnIndexOrThrow("id");
                int nameColumn = cursor.getColumnIndexOrThrow("name");
                int quantityColumn = cursor.getColumnIndexOrThrow("quantity");
                int unitColumn = cursor.getColumnIndexOrThrow("unit");
                int expiryColumn = cursor.getColumnIndexOrThrow("expiry");

                while (cursor.moveToNext()) {
                    Ingredient ingredient = new Ingredient(
                            cursor.getInt(idColumn),
                            cursor.getString(nameColumn),
                            cursor.getDouble(quantityColumn),
                            cursor.getString(unitColumn),
                            cursor.getString(expiryColumn)
                    );

                    allIngredients.add(ingredient);

                    if (ingredient.getQuantity() <= LOW_STOCK_THRESHOLD) {
                        lowStockCount++;
                    }

                    Date expiryDate = parseExpiryDate(ingredient.getExpiry());

                    if (expiryDate != null) {
                        if (expiryDate.before(today)) {
                            expiredCount++;
                        } else if (!expiryDate.after(threeDaysFromNow)) {
                            expiringCount++;
                        }
                    }
                }
            } catch (Exception e) {
                Toast.makeText(
                        this,
                        "Unable to load pantry ingredients: " + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            } finally {
                cursor.close();
            }
        }

        txtTotalIngredients.setText(String.valueOf(allIngredients.size()));
        txtExpiringSoon.setText(String.valueOf(expiringCount));
        txtExpired.setText(String.valueOf(expiredCount));

        if (lowStockCount > 0) {
            txtLowStockAlert.setText(
                    "Low stock: " + lowStockCount
                            + (lowStockCount == 1
                            ? " ingredient needs restocking."
                            : " ingredients need restocking.")
            );
            txtLowStockAlert.setVisibility(View.VISIBLE);
        } else {
            txtLowStockAlert.setVisibility(View.GONE);
        }

        applyFilters();
    }

    private void applyFilters() {
        if (adapter == null) {
            return;
        }

        ingredientList.clear();

        String searchText = edtSearchIngredient.getText()
                .toString().trim().toLowerCase(Locale.ROOT);

        String selectedFilter = spinnerIngredientFilter.getSelectedItem() == null
                ? "All ingredients"
                : spinnerIngredientFilter.getSelectedItem().toString();

        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        todayCalendar.set(Calendar.SECOND, 0);
        todayCalendar.set(Calendar.MILLISECOND, 0);

        Date today = todayCalendar.getTime();

        Calendar threeDaysCalendar = (Calendar) todayCalendar.clone();
        threeDaysCalendar.add(Calendar.DAY_OF_YEAR, 3);
        Date threeDaysFromNow = threeDaysCalendar.getTime();

        for (Ingredient ingredient : allIngredients) {
            String name = ingredient.getName() == null
                    ? ""
                    : ingredient.getName().toLowerCase(Locale.ROOT);

            if (!name.contains(searchText)) {
                continue;
            }

            Date expiryDate = parseExpiryDate(ingredient.getExpiry());

            switch (selectedFilter) {
                case "Low stock":
                    if (ingredient.getQuantity() > LOW_STOCK_THRESHOLD) {
                        continue;
                    }
                    break;

                case "Expiring soon":
                    if (expiryDate == null
                            || expiryDate.before(today)
                            || expiryDate.after(threeDaysFromNow)) {
                        continue;
                    }
                    break;

                case "Expired":
                    if (expiryDate == null || !expiryDate.before(today)) {
                        continue;
                    }
                    break;

                default:
                    break;
            }

            ingredientList.add(ingredient);
        }

        adapter.notifyDataSetChanged();

        if (ingredientList.isEmpty()) {
            if (allIngredients.isEmpty()) {
                txtEmptyPantry.setText(
                        "Your pantry is empty.\n\n"
                                + "Add your first ingredient to get started!"
                );
            } else {
                txtEmptyPantry.setText(
                        "No ingredients match your search or filter."
                );
            }

            txtEmptyPantry.setVisibility(View.VISIBLE);
            recyclerPantry.setVisibility(View.GONE);
        } else {
            txtEmptyPantry.setVisibility(View.GONE);
            recyclerPantry.setVisibility(View.VISIBLE);
        }
    }

    private Date parseExpiryDate(String expiry) {
        if (expiry == null || expiry.trim().isEmpty()) {
            return null;
        }

        String cleanExpiry = expiry.trim();

        SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dateFormat.setLenient(false);

        ParsePosition position = new ParsePosition(0);
        Date parsedDate = dateFormat.parse(cleanExpiry, position);

        if (parsedDate == null
                || position.getIndex() != cleanExpiry.length()) {
            return null;
        }

        return parsedDate;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}
