
package com.example.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PantryActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerPantry;
    private TextView txtEmptyPantry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        Button btnAddIngredient = findViewById(R.id.btnAddIngredient);
        recyclerPantry = findViewById(R.id.recyclerPantry);
        txtEmptyPantry = findViewById(R.id.txtEmptyPantry);

        databaseHelper = new DatabaseHelper(this);

        recyclerPantry.setLayoutManager(new LinearLayoutManager(this));

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
        Cursor cursor = databaseHelper.getAllIngredients();

        IngredientAdapter adapter = new IngredientAdapter(this, cursor);
        recyclerPantry.setAdapter(adapter);

        if (cursor.getCount() == 0) {
            txtEmptyPantry.setVisibility(View.VISIBLE);
        } else {
            txtEmptyPantry.setVisibility(View.GONE);
        }
    }
}
