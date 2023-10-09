pluginManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "DemoMovieFlow"
include(":app")
include(":domain")
include(":data")
include(":framework")
include(":features:feature_splash")
include(":features:feature_error_message")
include(":features:feature_popular_movies")
include(":features:feature_search_movie")
include(":features:feature_favorite_movies")
include(":features:feature_movie_details")
