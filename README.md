# DemoMovieFlow-Android

**Screenshots:**
| <img src="docs/screenshots/splash.jpg">          | <img src="docs/screenshots/popular_movies.jpg"> | <img src="docs/screenshots/search_movie.jpg"> |
| ------------------------------------------------ | ----------------------------------------------- | --------------------------------------------- |
| <img src="docs/screenshots/favorite_movies.jpg"> | <img src="docs/screenshots/movie_details.jpg">  | <img src="docs/screenshots/burger_menu.jpg">  |

**Technology Stack:**
- Kotlin Programming Language
- Clean Architecture
- MVVM Architecture Pattern
- Hilt Dependency Injection
- Navigation Graph
- View Binding, Data Binding
- ViewModel
- LiveData
- ROOM Database
- Retrofit REST + OkHttp
- Coroutine
- Flow
- GSON Serialization
- Picasso Image Loader
- Unit Test: JUnit, Mockito, MockWebServer, Robolectric
- Code Coverage: JaCoCo
- Gradle build flavors
- BuildSrc + Kotlin DSL
- Proguard
- Git

**To run the project in DEBUG MODE:**

Get the access token (not the api key) from [api.themoviedb.org](https://api.themoviedb.org/).

Create file namely “key.properties” in the root project with following contents:

storePassword=<your_keystore_password> <br />
keyPassword=<your_key_password> <br />
keyAlias=<your_key_alias> <br />
storeFile=<path_to_keystore_file> <br />
accessToken=<your_access_token> <br />

once you have created it, open the project with Android Studio, build the project and run the project.

**To run Code Coverage (JaCoCo):**
1. Open Terminal then move to "root_project" directory.
2. type "./gradlew codeCoverModules allDebugCodeCoverage" (enter), wait until finish executing.

The report file will be located in "root_project/build/reports/jacoco/allDebugCoverage/html/index.html", open it with browser.

**Caution:**
**Later I will add more unit tests to increase the code coverage value.**
