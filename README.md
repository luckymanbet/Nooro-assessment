# WeatherApp

WeatherApp is a modern Android application that provides real-time weather updates for various locations. It uses **Jetpack Compose** for its UI and follows **MVVM architecture** to ensure scalability and maintainability.

## Features

- **Search Weather**: Search for weather information by city name.
- **Real-Time Updates**: Pull-to-refresh functionality to update weather data instantly.
- **Temperature Units**: Toggle between Celsius and Fahrenheit.
- **Detailed Weather Information**: View comprehensive weather details like:
  - Temperature
  - Humidity
  - UV Index
  - Feels Like Temperature
- **Persistent Preferences**: Automatically fetches the weather for the last searched location on app launch.
- **Responsive UI**: Modern and user-friendly design using Jetpack Compose.

## Technologies Used

- **Jetpack Compose**: For building a modern and responsive UI.
- **Hilt**: For dependency injection.
- **Retrofit**: For making API calls to fetch weather data.
- **SharedPreferences**: To save user preferences like the last searched location and temperature unit.
- **Coroutines & Flows**: To handle asynchronous operations and reactive streams.

### Testing Frameworks:
- **MockK**: For mocking dependencies in tests.
- **Turbine**: For testing Flow emissions.
- **Truth**: For assertions in unit tests.

## Prerequisites

- **Kotlin**: 2 or higher.
- **API Key**: A valid API key from [WeatherAPI](http://api.weatherapi.com/).
- **Minimum SDK**: 24
- **Compile SDK**: 35

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/WeatherApp.git
```

## 2. Configure the API Key

Navigate to `Constants.kt` in `com.hometest.weather.data.common`.

Replace the placeholder API key with your WeatherAPI key:

```kotlin
object Constants {
    const val BASE_URL = "https://api.weatherapi.com/v1/"
    const val API_KEY = "your_api_key_here" // Replace with your WeatherAPI key
}
```

## Project Structure

- **data**: Contains repositories, remote data sources, and models.
- **domain**: Defines use cases and business logic.
- **ui**: Handles the presentation layer, including screens and composables.
- **di**: Provides dependency injection modules using Hilt.
- **utils**: Helper classes for tasks like mapping weather conditions to icons.

---

## Home Screen

- **Search Bar**: Input for searching weather by city name.
- **Weather Card**: Displays an overview of weather conditions, including:
  - **Location**
  - **Temperature**
  - **Condition Icon**
- **Pull-to-Refresh**: Swipe down to refresh the weather for the current location.
- **Temperature Toggle**: A button to switch between Celsius and Fahrenheit.

---

## Detail Screen

- **Weather Icon**: Displays a relevant icon for the weather condition.
- **Temperature Display**: Shows the temperature in the selected unit.
- **Additional Details**: Includes:
  - **Humidity**
  - **UV Index**
  - **"Feels Like" Temperature**

---

## Unit Testing

The app includes unit tests
