
package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPantry = findViewById(R.id.btnPantry);
        Button btnSuggestions = findViewById(R.id.btnSuggestions);
        Button btnSettings = findViewById(R.id.btnSettings);

        btnPantry.setOnClickListener(v ->
                openScreen(PantryActivity.class));

        btnSuggestions.setOnClickListener(v ->
                openScreen(SuggestedRecipesActivity.class));

        btnSettings.setOnClickListener(v ->
                openScreen(SettingsActivity.class));
    }

    private void openScreen(Class<?> screenClass) {
        startActivity(new Intent(this, screenClass));
    }
}
