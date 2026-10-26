# Smart Pantry Manager

## 1. Overview

Smart Pantry Manager is an Android application developed in Java to help users manage pantry ingredients, monitor expiry dates, and discover recipes using ingredients they already have. The application uses a local SQLite database to store pantry information and support data persistence.

The project focuses on simplifying pantry management, supporting meal planning, and helping users reduce food waste.

## 2. Features

- **Pantry management:** Add, view, edit, and delete pantry ingredients.
- **Ingredient details:** Record ingredient names, quantities, units, and expiry dates.
- **Input validation:** Validate ingredient information before saving.
- **Recipe suggestions:** View recipes that match ingredients available in the pantry.
- **Recipe details:** View recipe ingredients and preparation instructions.
- **Settings:** Manage available settings preferences, including expiry reminders and low-stock alerts.
- **Navigation:** Move between the home screen, pantry, recipe suggestions, recipe details, and settings.
- **Local storage:** Save pantry and recipe data using SQLite.

## 3. Technologies Used

- **Programming language:** Java
- **Development environment:** Android Studio
- **Database:** SQLite
- **User interface:** XML layouts
- **Platform:** Android
- **Testing:** JUnit unit tests

## 4. System Requirements

- Android Studio
- The Android SDK required by the project
- A compatible Android emulator or physical Android device
- A compatible JDK and Gradle configuration

## 5. Installation and Setup

1. Clone or download this repository.
2. Open Android Studio.
3. Select **Open** and choose the project root folder.
4. Allow Gradle to synchronise and resolve project dependencies.
5. Start an Android emulator or connect an Android device with USB debugging enabled.
6. Select the application configuration and click **Run**.
7. Wait for the application to build and launch.

## 6. How to Use the Application

1. Launch Smart Pantry Manager.
2. Open **My Pantry** to view stored ingredients.
3. Add an ingredient by entering its name, quantity, unit, and expiry date.
4. Save the ingredient after completing the required fields.
5. Edit an existing ingredient or delete it when it is no longer needed.
6. Open **Recipe Suggestions** to view available recipe recommendations.
7. Select a recipe to view its ingredients and preparation instructions.
8. Open **Settings** to manage expiry reminder and low-stock alert preferences.
9. Use the available Home navigation buttons to return to the relevant main screen.

## 7. Project Structure

The project contains Java source files, XML layouts, drawable resources, database functionality, and unit tests.

Important source files include:

- `MainActivity.java` — Main dashboard and navigation.
- `PantryActivity.java` — Pantry ingredient management.
- `AddEditIngredientActivity.java` — Adding and editing ingredients.
- `SuggestedRecipesActivity.java` — Displaying recipe suggestions.
- `SettingsActivity.java` — Settings and preference management.
- `DatabaseHelper.java` — SQLite database operations and recipe storage.
- `IngredientValidator.java` — Ingredient input validation.
- `res/layout/` — XML screen layouts.
- `res/drawable/` — Drawable resources and interface backgrounds.
- `res/values/` — Themes, colours, and other application resources.
- `app/src/test/` — Unit tests.

## 8. Database Design

Smart Pantry Manager uses SQLite to store application data locally on the device.

### Pantry Ingredients

Each pantry ingredient includes:

- Ingredient ID
- Ingredient name
- Quantity
- Unit
- Expiry date

The database supports creating, retrieving, updating, and deleting pantry ingredient records.

### Recipe Storage

The database also stores recipes, their required ingredients, and their preparation instructions. Recipe records are associated with their ingredient requirements.

The Suggested Recipes screen uses the application's recipe-matching functionality to recommend recipes based on the user's pantry contents. Recipe availability depends on the matching rules implemented in the application.

### Data Persistence

Pantry records and recipe data are stored locally in SQLite. Settings preferences are stored separately using Android shared preferences. Data persistence and preference restoration should be verified during testing.

## 9. Testing

The project includes JUnit unit tests for ingredient input validation.

To run the unit tests on Windows, open the project terminal at the project root and execute:

```powershell
.\gradlew.bat test
```

A successful Gradle build indicates that the configured tests completed without a build failure. The application should also be tested manually on an emulator or Android device to verify pantry operations, navigation, recipe suggestions, recipe details, and settings persistence.

## 10. Future Improvements

Potential future enhancements include:

- Notifications for ingredients approaching their expiry dates.
- Ingredient search and filtering.
- More advanced recipe matching.
- Food-waste tracking and reporting.
- Additional accessibility and usability improvements.

## 11. Author

Developed as an Android application project.

## 12. License

No open-source licence has been specified for this project.
