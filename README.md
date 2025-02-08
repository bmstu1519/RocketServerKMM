<div align="center">

[android]: #1-android-module
[ios]: #2-ios-module--ios-native
[mvi+clean]: #mvi--clean-architecture-overview

<!--  <p><img src="https://github.com/bmstu1519/MVVMNewsApp/blob/master/app/src/main/res/drawable/no_content.png" width="200"></p> /> -->

# RocketServerKMM

[![Android](https://img.shields.io/badge/Android-grey?logo=android&style=flat)](https://www.android.com/)
[![iOS](https://img.shields.io/badge/iOS-grey?logo=apple)](https://www.apple.com/)

[![ComposeMultiplatform](https://img.shields.io/badge/compose--multiplatform-1.6.11-blue?logo=jetpackcompose)](https://github.com/JetBrains/compose-multiplatform)
[![Kotlin](https://img.shields.io/badge/kotlin-2.1.0-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Apollo](https://img.shields.io/badge/apollo%20graphql-4.0.0-blue?logo=apollo%20graphql)](https://www.apollographql.com/)


[![Ktor](https://img.shields.io/badge/ktor-2.3.12-blue?logo=ktor)](https://ktor.io/docs/client-create-multiplatform-application.html)
[![Voyager](https://img.shields.io/badge/voyager-1.0.0-blue)](https://voyager.adriel.cafe/)
[![Coil3](https://img.shields.io/badge/coil3-3.0.0--rc01-blue)](https://coil-kt.github.io/coil/compose/)


<p align="center"> 
  <a href='https://github.com/bmstu1519/RocketServerKMM/releases/download/1.0.0/android.zip'>
    <img alt='Download APK' src='https://github.com/bmstu1519/RocketServerKMM/blob/main/.github/assets/icons/android_apk.png' height=75/>
  </a>
  <a href='https://github.com/bmstu1519/RocketServerKMM/releases/download/1.0.0/ios.zip'>
    <img alt='Download APP' src='https://github.com/bmstu1519/RocketServerKMM/blob/main/.github/assets/icons/apple_app_black_white.png' height="75"/>
  </a>
</p>


</div>

<p align="center">
  <div style="position: relative; display: inline-block;">
    <img src="https://github.com/bmstu1519/RocketServerKMM/blob/main/.github/assets/screenshots/preview_placeholder.001.png"
         alt="Preview"
         width="100%"/>
    <img src="https://github.com/bmstu1519/RocketServerKMM/blob/main/.github/assets/screenshots/android_ios_preview.gif"
         alt="Preview"
         width="100%"
         style="position: absolute; top: 0; left: 0; opacity: 0; transition: opacity 0.3s;"
         onload="this.style.opacity='1'"/>
  </div>
</p>

## Features ✨

- **Upcoming Launches:** Stay informed about all scheduled rocket launches.
- **Secure Authorization:** Protect your bookings with user authentication.
- **Launch Booking System:** Reserve your spot on upcoming rocket flights.
- **Theme Customization:** Switch between light and dark modes for optimal viewing.

<br>

# Architecture 🏗️

## MVI + Clean Architecture Overview

This application is built upon the principles of MVI (Model-View-Intent) and Clean Architecture.
This combination provides a robust, scalable, and maintainable foundation.
MVI facilitates a unidirectional data flow and clear separation of concerns, while Clean Architecture ensures a modular and testable structure.

<div align="center">
  <a href="https://github.com/bmstu1519/RocketServerKMM/blob/main/.github/assets/diagrams/mvi_clean.png" target="_blank">
    <img src="https://github.com/bmstu1519/RocketServerKMM/blob/main/.github/assets/diagrams/mvi_clean.svg" 
      width="1200">
  </a>
  <br>
  <em>Diagram 1: MVI + Clean Architecture Flow (click to enlarge)</em>
</div>
<br>

The diagram illustrates the application's clean architecture implementation with MVI pattern, divided into three main layers:

1. **Presentation Layer**
- **Screens**: Compose Multiplatform UI components with Coil3 for efficient image loading
- **ViewModel**: Implements MVI pattern with:
  - Reducer: Handles state mutations
  - State: Maintains UI state
  - Actions: Processes user interactions

2. **Domain Layer**
- **UseCase**: Contains business logic
- **Repository**: Defines data operations contract
- **Model**: Core business entities
- **ModelMapping.kt**: Handles conversion between domain models and DTOs

3. **Data Layer**
- **Network**:
  - Ktor client implementation with GraphQL
  - Apollo Kotlin for type-safe GraphQL operations with auto-generated models
  - Integrated logging for network operations

- **Local Cache**:
  - Implements KeyVault for secure cross-platform data storage
  - Platform-specific native implementations:
    - Android: SharedPreferences with encryption
    - iOS: Keychain Services

**Key Technical Features:**
- GraphQL client eliminates need for manual DTO creation
- Coil3 handles efficient image loading in Compose Multiplatform
- Clean separation of concerns with unidirectional data flow
- Type-safe GraphQL operations with Apollo Kotlin
- Secure cross-platform data storage with KeyVault's native implementations

## Application Architecture

After examining the core architectural patterns (MVI + Clean Architecture), let's look at the complete application structure that demonstrates how all components work together.

<div align="center">
  <a href="https://github.com/bmstu1519/RocketServerKMM/blob/main/.github/assets/diagrams/kmm_rocket_reserved_arch.png" target="_blank">
    <img src="https://github.com/bmstu1519/RocketServerKMM/blob/main/.github/assets/diagrams/kmm_rocket_reserved_arch.svg" 
      width="1200">
  </a>
  <br>
  <em>Diagram 2: Complete Application Architecture (click to enlarge)</em>
</div>
<br>

This diagram showcases the overall application architecture, highlighting the integration of Compose Multiplatform, platform-specific modules, and dependency injection.

**1. composeApp (Cross-platform Module)**

- **commonMain**: Contains the core application logic, including:
  - App.kt: The main Compose application entry point, used by both Android and iOS modules.
  - platform/expect declarations for platform-specific functionalities.
  - See the [mvi+clean] section for details.

- **iosMain**: See the [ios] section for details.

- **androidMain**: See the [android] section for details.

**2. Dependency Injection (DI)**

The following modules are used for DI configuration:

* **`clientsModule`**:  Provides network clients and related dependencies.
* **`appModule`**:  Provides application-level dependencies.
* **`scopeModule`**:  Defines scopes for managing shared data and holding state.

```kotlin
fun initKoin(config: KoinAppDeclaration? = {}) =
  startKoin {
    config?.invoke(this)
    modules(clientsModule, appModule, scopeModule)
  }
```

**3. Expect/Actual Mechanism**

- The expect/actual mechanism enables platform-specific implementations of common interfaces and functionalities.
- Expect and actual functions are tightly coupled, ensuring consistent behavior across platforms.

## Platform-Specific Architectures

This section provides a deeper dive into the architecture of each platform, showcasing how the core principles are applied in different contexts.

### 1. Android module

<div align="center">
  <a href="https://github.com/bmstu1519/RocketServerKMM/blob/main/.github/assets/diagrams/androidMain_arch.png" target="_blank">
    <img src="https://github.com/bmstu1519/RocketServerKMM/blob/main/.github/assets/diagrams/androidMain_arch.svg" 
      width="1200">
  </a>
  <br>
  <em>Diagram 3: Android module Architecture(click to enlarge)</em>
</div>
<br>

The diagram shows the androidMain module structure, which consists of three main components:

1. **MainActivity.kt**
- Contains `setContent { App() }` function responsible for initializing and displaying the main UI using Compose

2. **Application**
- Features two key initialization functions:
  - `initKoin()` for Dependency Injection setup
  - `initKVault()` for secure storage initialization

3. **platform/actual**
- Contains platform-specific Android implementations:
  - `AlertDialog.android.kt` - native dialog windows implementation
  - `Engine.android.kt` - Android network client powered by OkHttp
  - `KVault.android.kt` - Android secure storage implementation that combines SharedPreferences with encryption for secure data persistence
  - `Theme.android.kt` - Android theme settings

### 2. iOS module + iOS (Native)
<div align="center">
  <a href="https://github.com/bmstu1519/RocketServerKMM/blob/main/.github/assets/diagrams/iosMain_arch.png" target="_blank">
    <img src="https://github.com/bmstu1519/RocketServerKMM/blob/main/.github/assets/diagrams/iosMain_arch.svg" 
      width="1200">
  </a>
  <br>
  <em>Diagram 4: iOS module & iOS native Architecture communication(click to enlarge)</em>
</div>
<br>

The diagram illustrates two main parts and their interaction:

1. **iosApp (Native Part)**
- Built with SwiftUI
- Contains `ContentView.swift` as the main UI container
- Includes `iOSApp.swift` as the application entry point
- Uses Swift and SwiftUI for native iOS implementation

2. **iosMain module**
- Contains `MainViewController.kt` with three key functions:
  - `initKoin()` for dependency injection setup
  - `initKVault()` for secure storage initialization
  - `setContent { App() }` for Compose UI initialization

- **platform/actual** includes iOS-specific implementations:
  - `AlertDialog.ios.kt` - dialog windows implementation
  - `Engine.ios.kt` - iOS network client using URLSession
  - `KVault.ios.kt` - iOS secure storage using Keychain
  - `Theme.ios.kt` - iOS theme settings

3. **Integration Bridge**
- `MainViewController(KMainViewController)` serves as a bridge between native iOS and Compose Multiplatform
- Enables seamless communication between SwiftUI and Compose UI components

### Cross-Platform Considerations

**Navigation:**

Voyager is used for navigation within the Compose Multiplatform UI.  
Tab navigation is implemented using a sealed class `TabItem` as an enum, defining each tab as an object within the sealed class.
```kotlin
sealed class TabItem : Tab {
  object LaunchesTab : TabItem() {
    // ...
  }

  object SettingsTab : TabItem() {
    // ...
  }
}
```
This approach provides type safety and clarity when working with tabs.  
Each `TabItem` object implements the `Tab` interface from Voyager and defines its content and options (icon, title).

Each tab's content is wrapped in a Navigator composable, allowing for independent navigation stacks within each tab.
This is facilitated by the use of LocalTabNavigator.current within the TabNavigationItem composable.
```kotlin
@Composable
fun RowScope.TabNavigationItem(tab: TabItem) {
  val tabNavigator = LocalTabNavigator.current

  NavigationBarItem(
    selected = tabNavigator.current == tab,
    onClick = { tabNavigator.current = tab },
    // ...
  )
}
```
Finally, the NavigationBar composable uses the TabNavigationItem to display the tabs in App.kt:
```kotlin
TabNavigator(TabItem.LaunchesTab) { tabNavigator ->
  Scaffold(
    topBar = { ... },
    bottomBar = {
      NavigationBar {
        TabNavigationItem(TabItem.LaunchesTab)
        TabNavigationItem(TabItem.SettingsTab)
      }
    }
  ) { ... }
```

**expect/actual fun:**

The `expect/actual` mechanism in Kotlin Multiplatform requires strict adherence to package structure.  
The declarations must be placed in corresponding paths across source sets.

`expect:`
```
commonMain/org.rocketserverkmm.project/platform
```

`actual:`
```
androidMain/org.rocketserverkmm.project/platform
iosMain/org.rocketserverkmm.project/platform
```

## Technical Details 🛠️

### UI
- Сompose Multiplatform

### Core
- Kotlin
- Coroutines
- Flow
- Kotlin-DSL

### Dependency Injection
- Koin:
  - Dependency injection
  - Screen parameters passing between screens

### Navigation
- Voyager

### Network
- Ktor:
  - Android: OkHttp engine
  - iOS: Darwin engine
- Apollo GraphQL:
  - Type-safe GraphQL client
  - Code generation for GraphQL queries
  - Automatic schema validation

### Storage
- KVault - secure key-value storage:
  - Android: EncryptedSharedPreferences (encrypted version of SharedPreferences)
  - iOS: Keychain (secure system-level storage)

### Image Loading
- Coil3

 ----

## Powered By ⚡

[Apollo Graphql](https://www.apollographql.com/docs/kotlin/tutorial)