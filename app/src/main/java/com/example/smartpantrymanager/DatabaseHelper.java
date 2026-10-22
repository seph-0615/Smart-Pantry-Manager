
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
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_PANTRY = "pantry";
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
        seedRecipes(db);
    }

    private void createPantryTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS pantry (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "quantity REAL NOT NULL, " +
                "unit TEXT NOT NULL, " +
                "expiry TEXT)");
    }

    private void createRecipeTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL UNIQUE, " +
                "method TEXT NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS recipe_ingredients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recipe_id INTEGER NOT NULL, " +
                "ingredient_name TEXT NOT NULL, " +
                "quantity REAL NOT NULL, " +
                "unit TEXT NOT NULL)");
    }

    private void seedRecipes(SQLiteDatabase db) {
        addRecipe(db, "Egg Fried Rice",
                "1. Cook the rice.\n2. Scramble the eggs in a pan.\n" +
                        "3. Add oil and rice, then stir-fry.",
                new String[][]{
                        {"rice", "1", "cup"},
                        {"egg", "2", "pieces"},
                        {"oil", "1", "tbsp"}
                });

        addRecipe(db, "Tomato Pasta",
                "1. Cook the pasta.\n2. Cook chopped tomatoes in oil.\n" +
                        "3. Mix the sauce with the pasta.",
                new String[][]{
                        {"pasta", "200", "g"},
                        {"tomato", "2", "pieces"},
                        {"oil", "1", "tbsp"}
                });

        addRecipe(db, "Vegetable Omelette",
                "1. Beat the eggs.\n2. Chop the onion and tomato.\n" +
                        "3. Cook everything in a pan.",
                new String[][]{
                        {"egg", "2", "pieces"},
                        {"onion", "1", "pieces"},
                        {"tomato", "1", "pieces"}
                });

        addRecipe(db, "Banana Pancakes",
                "1. Mash the banana.\n2. Mix with egg and flour.\n" +
                        "3. Cook small pancakes in a pan.",
                new String[][]{
                        {"banana", "1", "pieces"},
                        {"egg", "1", "pieces"},
                        {"flour", "100", "g"}
                });

        addRecipe(db, "Potato and Onion Fry",
                "1. Chop potatoes and onion.\n" +
                        "2. Heat oil in a pan.\n3. Fry until tender.",
                new String[][]{
                        {"potato", "2", "pieces"},
                        {"onion", "1", "pieces"},
                        {"oil", "1", "tbsp"}
                });

        addRecipe(db, "Rice and Beans",
                "1. Cook the rice.\n2. Heat the beans thoroughly.\n" +
                        "3. Serve together.",
                new String[][]{
                        {"rice", "1", "cup"},
                        {"beans", "1", "cup"}
                });

        addRecipe(db, "Tomato Egg Scramble",
                "1. Chop tomatoes.\n2. Cook them in oil.\n" +
                        "3. Add beaten eggs and cook thoroughly.",
                new String[][]{
                        {"tomato", "2", "pieces"},
                        {"egg", "2", "pieces"},
                        {"oil", "1", "tbsp"}
                });

        addRecipe(db, "Garlic Pasta",
                "1. Cook pasta.\n2. Gently fry garlic in oil.\n" +
                        "3. Mix together.",
                new String[][]{
                        {"pasta", "200", "g"},
                        {"garlic", "2", "pieces"},
                        {"oil", "1", "tbsp"}
                });

        addRecipe(db, "Potato Egg Hash",
                "1. Dice the potatoes.\n2. Fry in oil until tender.\n" +
                        "3. Add eggs and cook thoroughly.",
                new String[][]{
                        {"potato", "2", "pieces"},
                        {"egg", "2", "pieces"},
                        {"oil", "1", "tbsp"}
                });

        addRecipe(db, "Carrot Soup",
                "1. Chop carrots and onion.\n2. Boil in water until tender.\n" +
                        "3. Mash or blend.",
                new String[][]{
                        {"carrot", "3", "pieces"},
                        {"onion", "1", "pieces"}
                });

        addRecipe(db, "Banana Oatmeal",
                "1. Cook oats in water.\n2. Slice the banana.\n" +
                        "3. Serve the banana with the oats.",
                new String[][]{
                        {"oats", "50", "g"},
                        {"banana", "1", "pieces"}
                });

        addRecipe(db, "Cheese Omelette",
                "1. Beat the eggs.\n2. Cook in a lightly oiled pan.\n" +
                        "3. Add cheese and fold.",
                new String[][]{
                        {"egg", "2", "pieces"},
                        {"cheese", "30", "g"},
                        {"oil", "1", "tbsp"}
                });

        addRecipe(db, "Garlic Rice",
                "1. Cook rice.\n2. Fry garlic in oil.\n" +
                        "3. Add rice and stir.",
                new String[][]{
                        {"rice", "1", "cup"},
                        {"garlic", "2", "pieces"},
                        {"oil", "1", "tbsp"}
                });

        addRecipe(db, "Tomato and Onion Salad",
                "1. Chop tomatoes and onion.\n" +
                        "2. Mix in a bowl.\n3. Serve fresh.",
                new String[][]{
                        {"tomato", "2", "pieces"},
                        {"onion", "1", "pieces"}
                });

        addRecipe(db, "Flour Flatbread",
                "1. Mix flour and water into dough.\n" +
                        "2. Knead and flatten.\n3. Cook in a hot pan.",
                new String[][]{
                        {"flour", "200", "g"},
                        {"water", "100", "ml"}
                });
    }

    private void addRecipe(SQLiteDatabase db, String name,
                           String method, String[][] ingredients) {
        ContentValues recipe = new ContentValues();
        recipe.put("name", name);
        recipe.put("method", method);

        long recipeId = db.insertWithOnConflict(
                "recipes", null, recipe, SQLiteDatabase.CONFLICT_IGNORE);

        if (recipeId == -1) {
            try (Cursor c = db.query(
                    "recipes", new String[]{"id"}, "name = ?",
                    new String[]{name}, null, null, null)) {
                if (c.moveToFirst()) {
                    recipeId = c.getLong(0);
                }
            }
        }

        if (recipeId == -1) return;

        for (String[] ingredient : ingredients) {
            ContentValues values = new ContentValues();
            values.put("recipe_id", recipeId);
            values.put("ingredient_name", ingredient[0]);
            values.put("quantity", Double.parseDouble(ingredient[1]));
            values.put("unit", ingredient[2]);

            db.insert("recipe_ingredients", null, values);
        }
    }

    public boolean addIngredient(String name, double quantity,
                                 String unit, String expiry) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_UNIT, unit);
        values.put(COLUMN_EXPIRY, expiry);

        long result = db.insert(TABLE_PANTRY, null, values);
        db.close();
        return result != -1;
    }

    public boolean updateIngredient(int id, String name, double quantity,
                                    String unit, String expiry) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_UNIT, unit);
        values.put(COLUMN_EXPIRY, expiry);

        int result = db.update(TABLE_PANTRY, values,
                COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    public boolean deleteIngredient(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(TABLE_PANTRY,
                COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    public Cursor getAllIngredients() {
        return getReadableDatabase().query(
                TABLE_PANTRY, null, null, null, null, null,
                COLUMN_NAME + " ASC");
    }

    public Cursor getIngredientById(int id) {
        return getReadableDatabase().query(
                TABLE_PANTRY, null, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
    }

    public ArrayList<String[]> getMatchingRecipes() {
        ArrayList<String[]> matches = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        try (Cursor recipes = db.query(
                "recipes", new String[]{"id", "name", "method"},
                null, null, null, null, "name ASC")) {

            while (recipes.moveToNext()) {
                long recipeId = recipes.getLong(0);
                String recipeName = recipes.getString(1);
                String method = recipes.getString(2);
                boolean matchesAll = true;

                try (Cursor required = db.query(
                        "recipe_ingredients",
                        new String[]{"ingredient_name", "quantity", "unit"},
                        "recipe_id = ?", new String[]{String.valueOf(recipeId)},
                        null, null, null)) {

                    while (required.moveToNext()) {
                        if (!hasEnoughIngredient(
                                db,
                                required.getString(0),
                                required.getDouble(1),
                                required.getString(2))) {
                            matchesAll = false;
                            break;
                        }
                    }
                }

                if (matchesAll) {
                    matches.add(new String[]{recipeName, method});
                }
            }
        }

        return matches;
    }

    private boolean hasEnoughIngredient(SQLiteDatabase db,
                                        String name, double needed,
                                        String neededUnit) {
        try (Cursor pantry = db.query(
                TABLE_PANTRY,
                new String[]{COLUMN_NAME, COLUMN_QUANTITY, COLUMN_UNIT},
                null, null, null, null, null)) {

            while (pantry.moveToNext()) {
                String storedName = normalize(pantry.getString(0));
                String requiredName = normalize(name);

                if (!storedName.equals(requiredName)) continue;

                String storedUnit = pantry.getString(2);
                if (!unitType(storedUnit).equals(unitType(neededUnit))) {
                    continue;
                }

                double available = toBase(pantry.getDouble(1), storedUnit);
                double required = toBase(needed, neededUnit);

                if (available >= required) return true;
            }
        }

        return false;
    }

    private String normalize(String value) {
        String result = value.trim().toLowerCase(Locale.ROOT);
        if (result.endsWith("s") && result.length() > 3) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    private String unitType(String unit) {
        String u = unit.trim().toLowerCase(Locale.ROOT);

        if (u.equals("kg") || u.equals("kilogram") ||
                u.equals("kilograms") || u.equals("g") ||
                u.equals("gram") || u.equals("grams")) return "weight";

        if (u.equals("l") || u.equals("litre") || u.equals("litres") ||
                u.equals("liter") || u.equals("liters") ||
                u.equals("ml") || u.equals("millilitre") ||
                u.equals("millilitres")) return "volume";

        if (u.equals("piece") || u.equals("pieces") ||
                u.equals("pc") || u.equals("pcs")) return "pieces";

        return u;
    }

    private double toBase(double quantity, String unit) {
        String u = unit.trim().toLowerCase(Locale.ROOT);

        if (u.equals("kg") || u.equals("kilogram") ||
                u.equals("kilograms")) return quantity * 1000;

        if (u.equals("l") || u.equals("litre") || u.equals("litres") ||
                u.equals("liter") || u.equals("liters")) return quantity * 1000;

        return quantity;
    }
}
