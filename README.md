# BMI Calculator App

## Overview
The BMI Calculator App is an Android application designed to calculate Body Mass Index (BMI) based on user input for weight and height. This README provides an overview of the app's features, architecture, setup instructions, and usage guidelines.

## Features
- **Calculate BMI:** Enter weight and height to calculate BMI instantly.
- **Track History:** View a chart of the last 7 BMI entries.
- **Gender Selection:** Select between Male and Female for accurate BMI calculation.
- **Data Persistence:** Store BMI data locally using Room database.
- **Authentication:** Secure authentication with email & password and Google sign-in using Firebase.
- **Interactive UI:** Intuitive interface with seek bars for height selection and buttons for weight and age adjustments.

## Architecture
The app is built using MVVM (Model-View-ViewModel) architecture for separation of concerns and improved testability. Components include:
- **View:** Fragments for UI presentation.
- **ViewModel:** Manages UI-related data in a lifecycle-conscious way.
- **Model:** Data classes (`BMIData`) and repositories (`BMIRepository`) for data operations.
- **Room Database:** Local storage for BMI data with DAO (Data Access Object) pattern.
- **Firebase Authentication:** Secure authentication with email & password and Google sign-in.

## Screenshots
<div style="display: flex; flex-wrap: wrap; justify-content: center;">
    <img src="https://github.com/mohitsingh316/BMI-Calculator/assets/131430722/6205f07c-2098-4e22-b977-d934d6a65c74" alt="Screenshot 1" width="250" style="margin: 10px;">
    <img src="https://github.com/mohitsingh316/BMI-Calculator/assets/131430722/01cc1218-4b4f-4cf7-b0a9-da85be19dcc2" alt="Screenshot 2" width="250" style="margin: 10px;">
    <img src="https://github.com/mohitsingh316/BMI-Calculator/assets/131430722/e9acec45-89b8-41fe-b531-254de2fa191e" alt="Screenshot 3" width="250" style="margin: 10px;">
    <img src="https://github.com/mohitsingh316/BMI-Calculator/assets/131430722/8eecb264-1ae9-40c7-b012-70e943405a7c" alt="Screenshot 4" width="250" style="margin: 10px;">
    <img src="https://github.com/mohitsingh316/BMI-Calculator/assets/131430722/e34f45ca-c553-4a9e-88f8-c43c181100a1" alt="Screenshot 5" width="250" style="margin: 10px;">
    <img src="https://github.com/mohitsingh316/BMI-Calculator/assets/131430722/08d2207c-7dc0-41c3-916b-ddb565ff2011" alt="Screenshot 6" width="250" style="margin: 10px;">
</div>

## Download and Installation
To download and install the BMI Calculator app directly on your Android device:

1. **Go to the [Releases](https://github.com/username/BMICalculator/releases) page** of this repository.
2. **Download the latest APK file** (`BMI Calculator.apk`) from the Releases section.
3. **Transfer the APK file** to your Android device if you downloaded it on your computer.
4. **Enable installation from unknown sources**:
   - Open Settings on your Android device.
   - Navigate to Security (or Privacy) settings.
   - Enable "Unknown sources" or "Install unknown apps" option for your browser or file manager app.
5. **Open the APK file** on your Android device and follow the on-screen instructions to install the app.

## Firebase Integration
### Prerequisites
Before integrating Firebase Authentication into your project, make sure you have set up Firebase in your project:
- Create a Firebase project in the [Firebase Console](https://console.firebase.google.com/).
- Add your Android app to the Firebase project and download the `google-services.json` file.
- Follow the Firebase setup instructions provided in the Firebase Console.

### Authentication Methods
- **Email & Password:** Users can sign up and sign in using their email address and a password securely stored in Firebase Authentication.
- **Google Sign-In:** Users can also sign in using their Google account for a seamless authentication experience.

### Usage
1. Launch the app on your Android device.
2. Sign up or sign in using either email & password or Google sign-in.
3. Select your gender (Male/Female).
4. Adjust height using the seek bar.
5. Adjust weight and age using the increment and decrement buttons.
6. Tap "Calculate BMI" to view your BMI result.
7. Navigate to view BMI history and chart.

## Contributing
Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature/awesome-feature`).
3. Make your changes.
4. Commit your changes (`git commit -am 'Add awesome feature'`).
5. Push to the branch (`git push origin feature/awesome-feature`).
6. Create a new Pull Request.

## Acknowledgments
- Inspired by the need for a simple BMI Calculator app with history tracking.
- Thanks to the Android community and libraries used in this project.
---
