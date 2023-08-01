# ComposeMapBox

The `ComposeMapBox` Using
[mapbox-maps-android](https://github.com/mapbox/mapbox-maps-android) and
[mapbox-navigation-android](https://github.com/mapbox/mapbox-navigation-android)
to routing between two points. it
has been built with `Modular` and `Clean Architecture` principles, Repository Pattern, and MVVM
pattern as well as Architecture Components also
utilized with `Android Jetpack` contains: Compose, Hilt and ...

This app shows the usage of the new Android Jetpack and Architecture Components.

![VID_20230801_225239_501_2_2](https://github.com/sepehrsadri/ComposeMapBox/assets/41581915/d32a4118-05b2-40f4-ad23-fd9db29eb669)


**App features:**

- Show map and current location
- Selecting origin and destination point
- Routing between points
- Driving Mode

## Architecture

Uses concepts of the notorious Uncle Bob's architecture
called [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
.

* Better separation of concerns. Each module has a clear API., Feature related classes life in
  different modules and can't be referenced without explicit module dependency.
* Features can be developed in parallel eg. by different teams
* Each feature can be developed in isolation, independently from other features
* faster compile time

## Modules:

* **app** - It uses all the components and classes related to Android Framework. It gets the data
  from other modules and shows on UI. (**access all the modules**)
* **feature-map** - this feature module contains main screen and showing map
* **library** - contains different modules such as **library:core**, **library:designsystem**, **library:
  location**, **library:navigation**
  which contains business logic such as location and navigation.

## Tech stack - Library:

- [Kotlin](https://kotlinlang.org/) - Kotlin is a cross-platform, statically typed, general-purpose
  programming language with type inference. Kotlin is designed to interoperate fully with Java, and
  the JVM version of Kotlin's standard library depends on the Java Class Library, but type inference
  allows its syntax to be more concise.
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - A coroutine is a concurrency design
  pattern that you can use on Android to simplify code that executes asynchronously
- [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - for
  dependency injection.
- JetPack
    - [Compose]("https://developer.android.com/jetpack/compose") - Jetpack Compose is Android’s
      recommended modern toolkit for building native UI. It simplifies and accelerates UI
      development on Android. Quickly bring your app to life with less code, powerful tools, and
      intuitive Kotlin APIs.
    - [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) - Used get
      lifecyle event of an activity or fragment and performs some action in response to change
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores
      UI-related data that isn't destroyed on UI changes.
- [Material-Components](https://github.com/material-components/material-components-android) -
  Material design components.
- [Retrofit](https://github.com/square/retrofit) - Used for REST api communication.
- [OkHttp](http://square.github.io/okhttp/) - HTTP client that's efficient by default: HTTP/2
  support allows all requests to the same host to share a socket
- [Gson](https://github.com/google/gson) - Used to convert Java Objects into their JSON
  representation and vice versa.
- [Glide](https://github.com/bumptech/glide) - An image loading library for Android backed by Kotlin
  Coroutines.
- [Timber]("https://github.com/JakeWharton/timber") - This is a logger with a small, extensible API
  which provides utility on top of Android's normal Log class.
- [mapbox-navigation-android]("https://github.com/mapbox/mapbox-navigation-android") - Mapbox Navigation SDK for Android.
- [mapbox-maps-android]("https://github.com/mapbox/mapbox-maps-android") - Interactive, thoroughly customizable maps in native Android powered by vector tiles and OpenGL.

## Future Road Map

- add test cases especially for critical components
- UI test for screens
- Add Drive Mode
- more features to be implemented
