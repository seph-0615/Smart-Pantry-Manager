
package com.example.smartpantrymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchExpiryReminders;
    private SwitchCompat switchLowStockAlerts;
    private Button btnResetSettings;

    private SharedPreferences preferences;

    private static final String PREFS_NAME = "SmartPantrySettings";
    private static final String KEY_EXPIRY_REMINDERS = "expiry_reminders";
    private static final String KEY_LOW_STOCK_ALERTS = "low_stock_alerts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Home navigation
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

        // Connect the controls to the XML layout
        switchExpiryReminders =
                findViewById(R.id.switchExpiryReminders);

        switchLowStockAlerts =
                findViewById(R.id.switchLowStockAlerts);

        btnResetSettings =
                findViewById(R.id.btnResetSettings);

        // Load previously saved settings
        if (switchExpiryReminders != null) {
            switchExpiryReminders.setChecked(
                    preferences.getBoolean(KEY_EXPIRY_REMINDERS, true)
            );

            switchExpiryReminders.setOnCheckedChangeListener(
                    (buttonView, isChecked) -> {
                        preferences.edit()
                                .putBoolean(KEY_EXPIRY_REMINDERS, isChecked)
                                .apply();

                        showMessage(isChecked
                                ? "Expiry reminders enabled"
                                : "Expiry reminders disabled");
                    }
            );
        }

        if (switchLowStockAlerts != null) {
            switchLowStockAlerts.setChecked(
                    preferences.getBoolean(KEY_LOW_STOCK_ALERTS, true)
            );

            switchLowStockAlerts.setOnCheckedChangeListener(
                    (buttonView, isChecked) -> {
                        preferences.edit()
                                .putBoolean(KEY_LOW_STOCK_ALERTS, isChecked)
                                .apply();

                        showMessage(isChecked
                                ? "Low-stock alerts enabled"
                                : "Low-stock alerts disabled");
                    }
            );
        }

        // Reset preferences
        if (btnResetSettings != null) {
            btnResetSettings.setOnClickListener(v -> resetSettings());
        }
    }

    private void resetSettings() {
        preferences.edit()
                .putBoolean(KEY_EXPIRY_REMINDERS, true)
                .putBoolean(KEY_LOW_STOCK_ALERTS, true)
                .apply();

        if (switchExpiryReminders != null) {
            switchExpiryReminders.setChecked(true);
        }

        if (switchLowStockAlerts != null) {
            switchLowStockAlerts.setChecked(true);
        }

        showMessage("Settings restored to defaults");
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
