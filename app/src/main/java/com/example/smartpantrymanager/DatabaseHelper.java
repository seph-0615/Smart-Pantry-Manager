
package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 3;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_UNIT = "unit";
    public static final String COLUMN_EXPIRY = "expiry";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createPantryTable(db);
        createRecipeTables(db);
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        createPantryTable(db);
        createRecipeTables(db);

        db.delete("recipe_ingredients", null, null);
        seedRecipes(db);
    }

    private void createPantryTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS pantry (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "quantity REAL NOT NULL, " +
                        "unit TEXT NOT NULL, " +
                        "expiry TEXT)"
        );
    }

    private void createRecipeTables(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS recipes (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT UNIQUE NOT NULL, " +
                        "method TEXT NOT NULL)"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS recipe_ingredients (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "recipe_id INTEGER NOT NULL, " +
                        "ingredient_name TEXT NOT NULL, " +
                        "quantity REAL NOT NULL, " +
                        "unit TEXT NOT NULL, " +
                        "FOREIGN KEY(recipe_id) REFERENCES recipes(id))"
        );
    }

    private void seedRecipes(SQLiteDatabase db) {
        addRecipe(db, "Egg Fried Rice",
                "1. Cook the rice.\n2. Heat oil in a pan.\n" +
                        "3. Add the eggs and stir.\n4. Add the rice and mix well.",
                new String[]{"rice", "egg", "oil"},
                new double[]{1, 2, 1},
                new String[]{"cup", "pieces", "tbsp"});

        addRecipe(db, "Tomato Pasta",
                "1. Cook the pasta.\n2. Chop the tomatoes.\n" +
                        "3. Heat oil in a pan.\n4. Add tomatoes and cook.\n" +
                        "5. Mix with the pasta.",
                new String[]{"pasta", "tomato", "oil"},
                new double[]{200, 2, 1},
                new String[]{"g", "pieces", "tbsp"});

        addRecipe(db, "Vegetable Omelette",
                "1. Beat the eggs.\n2. Chop the onion and tomato.\n" +
                        "3. Add vegetables to the eggs.\n4. Cook in a lightly oiled pan.",
                new String[]{"egg", "onion", "tomato"},
                new double[]{2, 1, 1},
                new String[]{"pieces", "pieces", "pieces"});

        addRecipe(db, "Banana Pancakes",
                "1. Mash the banana.\n2. Mix with egg and flour.\n" +
                        "3. Cook small portions in a pan until golden.",
                new String[]{"banana", "egg", "flour"},
                new double[]{1, 1, 100},
                new String[]{"pieces", "pieces", "g"});

        addRecipe(db, "Potato and Onion Fry",
                "1. Peel and chop the potatoes.\n2. Chop the onion.\n" +
                        "3. Fry both ingredients until tender.",
                new String[]{"potato", "onion", "oil"},
                new double[]{2, 1, 1},
                new String[]{"pieces", "pieces", "tbsp"});

        addRecipe(db, "Rice and Beans",
                "1. Cook the rice.\n2. Heat the beans.\n3. Mix and serve.",
                new String[]{"rice", "beans"},
                new double[]{1, 1},
                new String[]{"cup", "cup"});

        addRecipe(db, "Tomato Egg Scramble",
                "1. Chop and cook the tomatoes.\n" +
                        "2. Add beaten eggs and stir until cooked.",
                new String[]{"tomato", "egg", "oil"},
                new double[]{2, 2, 1},
                new String[]{"pieces", "pieces", "tbsp"});

        addRecipe(db, "Garlic Pasta",
                "1. Cook the pasta.\n2. Chop the garlic.\n" +
                        "3. Fry garlic in oil.\n4. Add pasta and mix.",
                new String[]{"pasta", "garlic", "oil"},
                new double[]{200, 2, 1},
                new String[]{"g", "pieces", "tbsp"});

        addRecipe(db, "Potato Egg Hash",
                "1. Chop and cook the potatoes.\n" +
                        "2. Add eggs and cook until done.",
                new String[]{"potato", "egg", "oil"},
                new double[]{2, 2, 1},
                new String[]{"pieces", "pieces", "tbsp"});

        addRecipe(db, "Carrot Soup",
                "1. Peel and chop the carrots.\n2. Boil until soft.\n" +
                        "3. Blend or mash until smooth.",
                new String[]{"carrot", "onion"},
                new double[]{3, 1},
                new String[]{"pieces", "pieces"});

        addRecipe(db, "Banana Oatmeal",
                "1. Cook the oats according to the packet.\n" +
                        "2. Slice the banana and add it to the oats.",
                new String[]{"oats", "banana"},
                new double[]{1, 1},
                new String[]{"cup", "pieces"});

        addRecipe(db, "Cheese Omelette",
                "1. Beat the eggs.\n2. Add grated cheese.\n" +
                        "3. Cook in a pan until set.",
                new String[]{"egg", "cheese", "oil"},
                new double[]{2, 30, 1},
                new String[]{"pieces", "g", "tbsp"});

        addRecipe(db, "Garlic Rice",
                "1. Cook the rice.\n2. Fry chopped garlic in oil.\n" +
                        "3. Add rice and stir.",
                new String[]{"rice", "garlic", "oil"},
                new double[]{1, 2, 1},
                new String[]{"cup", "pieces", "tbsp"});

        addRecipe(db, "Tomato and Onion Salad",
                "1. Chop the tomatoes and onion.\n" +
                        "2. Mix in a bowl and serve.",
                new String[]{"tomato", "onion"},
                new double[]{2, 1},
                new String[]{"pieces", "pieces"});

        addRecipe(db, "Flour Flatbread",
                "1. Mix flour with enough water to form a dough.\n" +
                        "2. Knead, roll flat, and cook in a hot pan.",
                new String[]{"flour"},
                new double[]{200},
                new String[]{"g"});
    }

    private void addRecipe(
            SQLiteDatabase db,
            String name,
            String method,
            String[] ingredientNames,
            double[] quantities,
            String[] units) {

        ContentValues recipeValues = new ContentValues();
        recipeValues.put("name", name);
        recipeValues.put("method", method);

        db.insertWithOnConflict(
                "recipes",
                null,
                recipeValues,
                SQLiteDatabase.CONFLICT_IGNORE
        );

        int recipeId = -1;

        try (Cursor cursor = db.query(
                "recipes",
                new String[]{"id"},
                "name = ?",
                new String[]{name},
                null,
                null,
                null)) {

            if (cursor.moveToFirst()) {
                recipeId = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id"));
            }
        }

        if (recipeId == -1) {
            return;
        }

        for (int i = 0; i < ingredientNames.length; i++) {
            ContentValues values = new ContentValues();
            values.put("recipe_id", recipeId);
            values.put("ingredient_name", ingredientNames[i]);
            values.put("quantity", quantities[i]);
            values.put("unit", units[i]);

            db.insert("recipe_ingredients", null, values);
        }
    }

    // Returns true if the ingredient was saved successfully.
    public boolean addIngredient(
            String name,
            double quantity,
            String unit,
            String expiry) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name.trim());
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_UNIT, unit);
        values.put(COLUMN_EXPIRY, expiry);

        return db.insert("pantry", null, values) != -1;
    }

    public Cursor getAllIngredients() {
        SQLiteDatabase db = getReadableDatabase();

        return db.query(
                "pantry", null, null, null, null, null, "name ASC");
    }

    public Cursor getIngredientById(int id) {
        SQLiteDatabase db = getReadableDatabase();

        return db.query(
                "pantry",
                null,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );
    }

    public int updateIngredient(
            int id,
            String name,
            double quantity,
            String unit,
            String expiry) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name.trim());
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_UNIT, unit);
        values.put(COLUMN_EXPIRY, expiry);

        return db.update(
                "pantry",
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }

    public int deleteIngredient(int id) {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
                "pantry",
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }

    public Cursor getAllRecipes() {
        SQLiteDatabase db = getReadableDatabase();

        return db.query(
                "recipes", null, null, null, null, null, "name ASC");
    }

    public Cursor getRecipeIngredients(int recipeId) {
        SQLiteDatabase db = getReadableDatabase();

        return db.query(
                "recipe_ingredients",
                null,
                "recipe_id = ?",
                new String[]{String.valueOf(recipeId)},
                null,
                null,
                "ingredient_name ASC"
        );
    }

    public ArrayList<String[]> getMatchingRecipes() {
        ArrayList<String[]> matchingRecipes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        try (Cursor recipes = getAllRecipes()) {
            while (recipes.moveToNext()) {
                int recipeId = recipes.getInt(
                        recipes.getColumnIndexOrThrow("id"));
                String recipeName = recipes.getString(
                        recipes.getColumnIndexOrThrow("name"));
                String method = recipes.getString(
                        recipes.getColumnIndexOrThrow("method"));

                boolean canMake = true;
                boolean hasRequirements = false;

                try (Cursor requirements = getRecipeIngredients(recipeId)) {
                    while (requirements.moveToNext()) {
                        hasRequirements = true;

                        String ingredient = requirements.getString(
                                requirements.getColumnIndexOrThrow(
                                        "ingredient_name"));
                        double quantity = requirements.getDouble(
                                requirements.getColumnIndexOrThrow(
                                        "quantity"));
                        String unit = requirements.getString(
                                requirements.getColumnIndexOrThrow("unit"));

                        if (!hasEnoughIngredient(
                                db, ingredient, quantity, unit)) {
                            canMake = false;
                            break;
                        }
                    }
                }

                if (canMake && hasRequirements) {
                    matchingRecipes.add(new String[]{recipeName, method});
                }
            }
        }

        return matchingRecipes;
    }

    private boolean hasEnoughIngredient(
            SQLiteDatabase db,
            String requiredName,
            double requiredQuantity,
            String requiredUnit) {

        double available = 0;
        String requiredNameNormalized =
                normalizeIngredientName(requiredName);
        String requiredFamily = getUnitFamily(requiredUnit);

        try (Cursor pantry = db.query(
                "pantry",
                new String[]{"name", "quantity", "unit"},
                null, null, null, null, null)) {

            while (pantry.moveToNext()) {
                String name = pantry.getString(0);
                double quantity = pantry.getDouble(1);
                String unit = pantry.getString(2);

                if (!normalizeIngredientName(name)
                        .equals(requiredNameNormalized)) {
                    continue;
                }

                if (!getUnitFamily(unit).equals(requiredFamily)) {
                    continue;
                }

                available += convertToBase(quantity, unit);
            }
        }

        return available >= convertToBase(requiredQuantity, requiredUnit);
    }

    private String normalizeIngredientName(String name) {
        if (name == null) {
            return "";
        }

        String normalized = name.trim().toLowerCase(Locale.ROOT);

        if (normalized.endsWith("ies") && normalized.length() > 3) {
            return normalized.substring(0, normalized.length() - 3) + "y";
        }

        if (normalized.endsWith("s")
                && !normalized.endsWith("ss")
                && normalized.length() > 3) {
            return normalized.substring(0, normalized.length() - 1);
        }

        return normalized;
    }

    private String normalizeUnit(String unit) {
        if (unit == null) {
            return "";
        }

        String normalized = unit.trim().toLowerCase(Locale.ROOT);

        switch (normalized) {
            case "piece":
            case "pieces":
            case "pc":
            case "pcs":
                return "pieces";
            case "gram":
            case "grams":
                return "g";
            case "kilogram":
            case "kilograms":
                return "kg";
            case "millilitre":
            case "millilitres":
            case "milliliter":
            case "milliliters":
                return "ml";
            case "litre":
            case "litres":
            case "liter":
            case "liters":
                return "l";
            default:
                return normalized;
        }
    }

    private String getUnitFamily(String unit) {
        String normalized = normalizeUnit(unit);

        switch (normalized) {
            case "g":
            case "kg":
                return "weight";
            case "ml":
            case "l":
                return "liquid";
            case "pieces":
                return "pieces";
            default:
                return normalized;
        }
    }

    private double convertToBase(double quantity, String unit) {
        String normalized = normalizeUnit(unit);

        switch (normalized) {
            case "kg":
            case "l":
                return quantity * 1000.0;
            default:
                return quantity;
        }
    }
}
