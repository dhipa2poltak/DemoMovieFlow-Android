# DemoMovieFlow-Android

**Technology Stack:**
- Kotlin Programming Language
- Clean Architecture
- MVVM Architecture Pattern
- Hilt Dependency Injection
- Navigation Graph
- View Binding
- ViewModel
- LiveData
- ROOM Database
- Retrofit REST + OkHttp
- Coroutine
- Flow
- GSON Serialization
- Gradle build flavors
- BuildSrc + Kotlin DSL
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
