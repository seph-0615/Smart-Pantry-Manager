
package com.example.smartpantrymanager;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private LinearLayout recipeContainer;

    private static final int DARK_GREEN = 0xFF204C37;
    private static final int ORANGE = 0xFFF28C28;
    private static final int TEXT_GREY = 0xFF536357;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        databaseHelper = new DatabaseHelper(this);
        recipeContainer = findViewById(R.id.recipeContainer);

        // Return to the Home dashboard.
        Button btnBackHome = findViewById(R.id.btnBackHome);
        if (btnBackHome != null) {
            btnBackHome.setOnClickListener(v -> {
                Intent intent = new Intent(
                        SuggestedRecipesActivity.this,
                        MainActivity.class
                );
                intent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP
                );
                startActivity(intent);
                finish();
            });
        }

        // Refresh recipe suggestions.
        Button btnRefresh = findViewById(R.id.btnRefreshRecipes);
        if (btnRefresh != null) {
            btnRefresh.setOnClickListener(v -> showRecipes());
        }

        showRecipes();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (databaseHelper != null && recipeContainer != null) {
            showRecipes();
        }
    }

    private void showRecipes() {
        if (recipeContainer == null || databaseHelper == null) {
            return;
        }

        recipeContainer.removeAllViews();

        // Preserve the existing recipe-matching logic.
        ArrayList<String[]> recipes =
                databaseHelper.getMatchingRecipes();

        TextView heading = new TextView(this);
        heading.setText("Your Recipe Suggestions");
        heading.setTextSize(25);
        heading.setTextColor(DARK_GREEN);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setPadding(0, 8, 0, 8);
        recipeContainer.addView(heading);

        TextView description = new TextView(this);
        description.setText(
                "Discover delicious meals using ingredients in your pantry."
        );
        description.setTextSize(15);
        description.setTextColor(TEXT_GREY);
        description.setLineSpacing(3, 1.0f);
        description.setPadding(0, 0, 0, 20);
        recipeContainer.addView(description);

        if (recipes.isEmpty()) {
            TextView emptyMessage = new TextView(this);

            emptyMessage.setText(
                    "No matching recipes yet.\n\n"
                            + "Add ingredients to your pantry to discover "
                            + "meals you can prepare."
            );

            emptyMessage.setTextSize(16);
            emptyMessage.setTextColor(TEXT_GREY);
            emptyMessage.setGravity(Gravity.CENTER);
            emptyMessage.setLineSpacing(5, 1.0f);
            emptyMessage.setPadding(20, 28, 20, 28);

            emptyMessage.setBackground(
                    createBackground(0xFFFFFFFF, 0xFFDCE7DC, 18)
            );

            recipeContainer.addView(emptyMessage);
            return;
        }

        TextView count = new TextView(this);
        count.setText("FOUND " + recipes.size() + " RECIPE(S)");
        count.setTextSize(13);
        count.setTextColor(DARK_GREEN);
        count.setTypeface(null, Typeface.BOLD);
        count.setPadding(0, 0, 0, 18);
        recipeContainer.addView(count);

        for (String[] recipe : recipes) {
            if (recipe == null || recipe.length < 2) {
                continue;
            }

            String recipeName = recipe[0];
            String method = recipe[1];

            String status = recipe.length >= 3
                    ? recipe[2]
                    : "Recipe suggestion";

            addRecipeCard(recipeName, method, status);
        }
    }

    private void addRecipeCard(
            String recipeName,
            String method,
            String status) {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(20, 20, 20, 20);

        card.setBackground(
                createBackground(0xFFFFFFFF, 0xFFDCE7DC, 18)
        );

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        cardParams.setMargins(0, 0, 0, 20);
        card.setLayoutParams(cardParams);
        card.setElevation(5);

        // Orange accent strip.
        View accent = new View(this);
        accent.setBackground(createBackground(ORANGE, ORANGE, 4));

        LinearLayout.LayoutParams accentParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 5
                );

        accentParams.setMargins(0, 0, 0, 16);
        card.addView(accent, accentParams);

        // Recipe title.
        TextView name = new TextView(this);
        name.setText(recipeName);
        name.setTextSize(21);
        name.setTextColor(DARK_GREEN);
        name.setTypeface(null, Typeface.BOLD);
        name.setLineSpacing(3, 1.0f);
        card.addView(name);

        // Status badge.
        TextView statusView = new TextView(this);
        statusView.setText(status);
        statusView.setTextSize(12);
        statusView.setTypeface(null, Typeface.BOLD);
        statusView.setPadding(12, 8, 12, 8);

        boolean ready = status.equalsIgnoreCase("READY TO MAKE");

        int statusColor = ready ? 0xFF246B3B : 0xFF8A5A00;
        int statusBackground = ready ? 0xFFE6F4E8 : 0xFFFFF1D6;

        statusView.setTextColor(statusColor);
        statusView.setBackground(
                createBackground(statusBackground, statusBackground, 12)
        );

        LinearLayout.LayoutParams statusParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        statusParams.setMargins(0, 12, 0, 20);
        card.addView(statusView, statusParams);

        // Divider.
        View divider = new View(this);
        divider.setBackgroundColor(0xFFE5EBE5);

        LinearLayout.LayoutParams dividerParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 1
                );

        dividerParams.setMargins(0, 0, 0, 16);
        card.addView(divider, dividerParams);

        // Preparation heading.
        TextView methodHeading = new TextView(this);
        methodHeading.setText("PREPARATION");
        methodHeading.setTextSize(12);
        methodHeading.setLetterSpacing(0.08f);
        methodHeading.setTextColor(0xFF718071);
        methodHeading.setTypeface(null, Typeface.BOLD);
        card.addView(methodHeading);

        // Preparation instructions.
        TextView methodView = new TextView(this);
        methodView.setText(method);
        methodView.setTextSize(15);
        methodView.setTextColor(0xFF424B43);
        methodView.setLineSpacing(5, 1.0f);
        methodView.setPadding(0, 10, 0, 0);
        card.addView(methodView);

        recipeContainer.addView(card);
    }

    private GradientDrawable createBackground(
            int fillColor,
            int strokeColor,
            int cornerRadius) {

        GradientDrawable background = new GradientDrawable();
        float density = getResources().getDisplayMetrics().density;

        background.setColor(fillColor);
        background.setCornerRadius(cornerRadius * density);
        background.setStroke(1, strokeColor);

        return background;
    }
}
