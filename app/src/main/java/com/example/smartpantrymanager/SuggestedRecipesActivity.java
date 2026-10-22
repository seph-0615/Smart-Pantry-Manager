
package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private LinearLayout recipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        databaseHelper = new DatabaseHelper(this);
        recipeContainer = findViewById(R.id.recipeContainer);

        Button btnRefresh = findViewById(R.id.btnRefreshRecipes);
        btnRefresh.setOnClickListener(v -> showRecipes());

        showRecipes();
    }

    private void showRecipes() {
        recipeContainer.removeAllViews();

        ArrayList<String[]> recipes = databaseHelper.getMatchingRecipes();

        if (recipes.isEmpty()) {
            TextView emptyMessage = new TextView(this);
            emptyMessage.setText(
                    "No matching recipes found.\n\n" +
                            "Add more ingredients to your pantry, " +
                            "or check that quantities and units are correct."
            );
            emptyMessage.setTextSize(16);
            emptyMessage.setPadding(16, 24, 16, 24);
            recipeContainer.addView(emptyMessage);
            return;
        }

        TextView count = new TextView(this);
        count.setText("Found " + recipes.size() + " matching recipe(s)");
        count.setTextSize(18);
        count.setPadding(0, 8, 0, 16);
        recipeContainer.addView(count);

        for (String[] recipe : recipes) {
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(16, 16, 16, 16);
            card.setBackgroundColor(0xFFF1F8E9);

            LinearLayout.LayoutParams cardParams =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
            cardParams.setMargins(0, 0, 0, 16);
            card.setLayoutParams(cardParams);

            TextView name = new TextView(this);
            name.setText(recipe[0]);
            name.setTextSize(20);
            name.setTextColor(0xFF1B5E20);
            name.setTypeface(null, android.graphics.Typeface.BOLD);
            card.addView(name);

            TextView method = new TextView(this);
            method.setText(recipe[1]);
            method.setTextSize(16);
            method.setTextColor(0xFF333333);
            method.setPadding(0, 8, 0, 0);
            card.addView(method);

            recipeContainer.addView(card);
        }
    }
}
