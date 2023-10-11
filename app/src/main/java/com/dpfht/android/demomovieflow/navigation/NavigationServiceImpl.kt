package com.dpfht.android.demomovieflow.navigation

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import com.dpfht.android.demomovieflow.R
import com.dpfht.android.demomovieflow.data.model.Genre
import com.dpfht.android.demomovieflow.domain.entity.MovieEntity
import com.dpfht.android.demomovieflow.framework.commons.model.MovieArgModel
import com.dpfht.android.demomovieflow.framework.navigation.NavigationService
import com.google.gson.Gson

class NavigationServiceImpl(
  private val navController: NavController,
  private val activity: AppCompatActivity
): NavigationService {

  override fun navigateToMovieHome() {
    val navGraph = navController.navInflater.inflate(R.navigation.home_nav_graph)
    navController.graph = navGraph
  }

  override fun navigateToMovieDetails(movieId: Int, movieEntity: MovieEntity?) {
    var strModel = ""
    if (movieEntity != null) {
      val movieArgModel = MovieArgModel(
        id = movieEntity.id,
        title = movieEntity.title,
        overview = movieEntity.overview,
        imageUrl = movieEntity.imageUrl,
        genres = movieEntity.genres.map { Genre(it.id, it.name) }
      )

      strModel = Gson().toJson(movieArgModel)
    }

    val builder = Uri.Builder()
    builder.scheme("android-app")
      .authority("com.dpfht.android.demomovieflow")
      .appendPath("movie_details_fragment")
      .appendQueryParameter("movieId", "$movieId")
      .appendQueryParameter("movieModel", strModel)

    navController.navigate(NavDeepLinkRequest.Builder
      .fromUri(builder.build())
      .build())
  }

  override fun navigatoErrorMessage(message: String) {
    val builder = Uri.Builder()
    builder.scheme("android-app")
      .authority("com.dpfht.android.demomovieflow")
      .appendPath("error_message_dialog_fragment")
      .appendQueryParameter("message", message)

    navController.navigate(
      NavDeepLinkRequest.Builder
        .fromUri(builder.build())
        .build())
  }
}
