
package com.example.smartpantrymanager;

public final class IngredientValidator {

    private IngredientValidator() {
        // Utility class; no instances required.
    }

    public static String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Enter an ingredient name";
        }
        return null;
    }

    public static String validateQuantity(String quantityText) {
        if (quantityText == null || quantityText.trim().isEmpty()) {
            return "Enter a quantity";
        }

        final double quantity;

        try {
            quantity = Double.parseDouble(quantityText.trim());
        } catch (NumberFormatException e) {
            return "Enter a valid number";
        }

        if (Double.isNaN(quantity)
                || Double.isInfinite(quantity)
                || quantity <= 0) {
            return "Quantity must be greater than zero";
        }

        return null;
    }

    public static String validateUnit(String unit) {
        if (unit == null || unit.trim().isEmpty()) {
            return "Enter a unit, e.g. kg or pieces";
        }
        return null;
    }
}
