
package com.example.smartpantrymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchExpiryReminders;
    private Switch switchLowStockAlerts;

    private SharedPreferences preferences;

    private static final String PREFS_NAME = "SmartPantrySettings";
    private static final String KEY_EXPIRY_REMINDERS = "expiry_reminders";
    private static final String KEY_LOW_STOCK_ALERTS = "low_stock_alerts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Home navigation button.
        Button btnBackHome = findViewById(R.id.btnBackHome);

        if (btnBackHome != null) {
            btnBackHome.setOnClickListener(v -> {
                Intent intent = new Intent(
                        SettingsActivity.this,
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

        preferences = getSharedPreferences(
                PREFS_NAME, MODE_PRIVATE
        );

        switchExpiryReminders =
                findViewById(R.id.switchExpiryReminders);

        switchLowStockAlerts =
                findViewById(R.id.switchLowStockAlerts);

        Button btnResetSettings =
                findViewById(R.id.btnResetSettings);

        // Load saved preferences. Both are enabled by default.
        boolean expiryEnabled = preferences.getBoolean(
                KEY_EXPIRY_REMINDERS, true
        );

        boolean lowStockEnabled = preferences.getBoolean(
                KEY_LOW_STOCK_ALERTS, true
        );

        switchExpiryReminders.setChecked(expiryEnabled);
        switchLowStockAlerts.setChecked(lowStockEnabled);

        // Save changes to the expiry reminder preference.
        switchExpiryReminders.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    preferences.edit()
                            .putBoolean(KEY_EXPIRY_REMINDERS, isChecked)
                            .apply();

                    Toast.makeText(
                            this,
                            isChecked
                                    ? "Expiry reminders enabled"
                                    : "Expiry reminders disabled",
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );

        // Save changes to the low-stock preference.
        switchLowStockAlerts.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    preferences.edit()
                            .putBoolean(KEY_LOW_STOCK_ALERTS, isChecked)
                            .apply();

                    Toast.makeText(
                            this,
                            isChecked
                                    ? "Low-stock alerts enabled"
                                    : "Low-stock alerts disabled",
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );

        // Restore default settings.
        btnResetSettings.setOnClickListener(v -> {
            switchExpiryReminders.setChecked(true);
            switchLowStockAlerts.setChecked(true);

            preferences.edit()
                    .putBoolean(KEY_EXPIRY_REMINDERS, true)
                    .putBoolean(KEY_LOW_STOCK_ALERTS, true)
                    .apply();

            Toast.makeText(
                    this,
                    "Settings restored to defaults",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }
}
