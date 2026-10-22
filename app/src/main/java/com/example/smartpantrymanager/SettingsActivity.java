
package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView txtSettingsInfo = findViewById(R.id.txtSettingsInfo);

        txtSettingsInfo.setText(
                "Smart Pantry Manager Settings\n\n" +
                        "App: Smart Pantry Manager\n" +
                        "Storage: SQLite database\n\n" +
                        "Use the pantry screen to manage your ingredients."
        );
    }
}
