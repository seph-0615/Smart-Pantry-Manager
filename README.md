# Smart Pantry Manager

## Overview

Smart Pantry Manager is an Android application that helps users organise pantry ingredients, track expiry dates, and find recipe suggestions based on available ingredients. The application is designed to make pantry management easier and help reduce food waste.

## Features

- Add pantry ingredients.
- View and manage saved ingredients.
- Edit ingredient details.
- Delete ingredients.
- Record ingredient names, quantities, and units.
- Select ingredient expiry dates using a date picker.
- View recipe suggestions.
- Access the settings screen.
- Navigate between screens using Home buttons.
- Validate ingredient information before saving.

## Technologies Used

- **Language:** Java
- **IDE:** Android Studio
- **Database:** SQLite
- **Layouts:** XML
- **Platform:** Android

## Requirements

- Android Studio
- Android SDK required by the project
- A compatible Android emulator or Android device
- Java Development Kit compatible with the project's Gradle configuration

## Installation and Setup

1. Clone or download the Smart Pantry Manager repository.
2. Open Android Studio.
3. Select **Open** and choose the project folder.
4. Allow Gradle to synchronise.
5. Start an Android emulator or connect an Android device.
6. Click **Run** to build and launch the application.

## How to Use

1. Launch Smart Pantry Manager.
2. Select **My Pantry** to manage pantry ingredients.
3. Add an ingredient by entering its name, quantity, unit, and expiry date.
4. Save the ingredient.
5. Edit or delete ingredients as needed.
6. Open **Recipe Suggestions** to explore available recipes.
7. Use the Home button to return to the main dashboard.
8. Open **Settings** to access the settings screen.

## Project Structure

The project contains Java source files, XML layouts, drawable resources, and database functionality.

- `MainActivity.java` — Main dashboard and navigation.
- `PantryActivity.java` — Pantry management.
- `AddEditIngredientActivity.java` — Adding and editing ingredients.
- `SuggestedRecipesActivity.java` — Recipe suggestions.
- `SettingsActivity.java` — Settings screen.
- `DatabaseHelper.java` — SQLite database operations.
- `res/layout/` — Application screen layouts.
- `res/drawable/` — Custom backgrounds and drawable resources.
- `res/values/` — Application themes and resource values.

## Database

Smart Pantry Manager uses SQLite to store pantry information locally on the Android device. The database supports creating, viewing, updating, and deleting pantry ingredients.

Each pantry ingredient contains:
- Ingredient ID
- Ingredient name
- Quantity
- Unit
- Expiry date

### Recipe Storage and Matching

The application includes a recipe collection stored in the SQLite database. Each recipe contains a name, required ingredients, and preparation instructions.

The Suggested Recipes feature is intended to display only recipes whose required ingredients are available in the user's pantry in the required quantities. Recipes with missing ingredients must not appear in the strict suggestions list.

Recipe matching should also account for simple ingredient-name differences where supported by the implementation.

### Data Persistence

SQLite stores application data locally so saved pantry records can remain available after the application is closed and reopened. This behaviour must be verified through application testing.

## Future Improvements

- Notifications for ingredients approaching expiry.
- Enhanced recipe matching.
- Ingredient search and filtering.
- Improved food-waste tracking.

## Author

Developed as an Android application project.

## License

No open-source license has been specified for this project.
