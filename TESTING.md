# Smart Pantry Manager — Testing Guide

## 1. Introduction

This document describes the manual and automated tests used to verify the Smart Pantry Manager Android application. The tests cover pantry management, recipe suggestions, settings, data persistence, input validation, and application stability.

## 2. Testing Environment

- **Platform:** Android
- **Development environment:** Android Studio
- **Programming language:** Java
- **Database:** SQLite
- **Automated testing:** Gradle unit tests

## 3. Functional Testing

| Test ID | Feature | Test Procedure | Expected Result |
|---|---|---|---|
| TC01 | Launch application | Open the app | The home screen displays successfully |
| TC02 | Add pantry item | Enter valid ingredient details and save | The ingredient is saved and displayed |
| TC03 | Edit pantry item | Change an existing ingredient and save | The updated details are displayed |
| TC04 | Delete pantry item | Delete an existing ingredient | The ingredient is removed from the list |
| TC05 | Input validation | Submit invalid or incomplete ingredient details | Invalid input is rejected or an appropriate message is displayed |
| TC06 | Suggested recipes | Open the Suggested Recipes screen | Recipes matching available ingredients are displayed when matches exist |
| TC07 | Recipe details | Select a suggested recipe | The recipe details and preparation method are displayed |
| TC08 | Refresh suggestions | Use the refresh button | Recipe suggestions are refreshed |
| TC09 | Settings | Open the Settings screen | The settings screen opens successfully |
| TC10 | Data persistence | Add an ingredient, close and reopen the app | The saved ingredient remains available |

## 4. Automated Testing

The project includes Java unit tests for ingredient validation. Run the tests from the project root using PowerShell:

```powershell
.\gradlew.bat test
```

A successful test run should finish with a `BUILD SUCCESSFUL` message. Review the test output to confirm that the relevant tests passed.

## 5. Database Testing

The application uses SQLite to store pantry items and recipe information. Testing should confirm that pantry changes are saved correctly and that stored data remains available after restarting the application.

Recipe suggestions should be checked against the ingredients currently stored in the pantry. Test both cases where matching recipes exist and where no suitable recipes are available.

## 6. User Interface Testing

Check that:

- The home screen opens without crashing.
- Pantry management controls are accessible.
- The Settings and Suggested Recipes screens open correctly.
- Recipe details are readable.
- Buttons respond to user interaction.
- Validation messages are understandable.

## 7. Recording Test Results

Run each test on the Android emulator or a compatible Android device. Record the actual result as **Pass** or **Fail** only after performing the test. Document any unexpected behaviour and retest after fixing it.

## 8. Conclusion

This testing guide provides a structured approach to checking the application's main features. Final test results should reflect the behaviour observed during actual testing.
