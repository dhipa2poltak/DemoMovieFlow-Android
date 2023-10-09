package com.dpfht.android.demomovieflow.navigation

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import com.dpfht.android.demomovieflow.R
import com.dpfht.android.demomovieflow.framework.navigation.NavigationService

class NavigationServiceImpl(
  private val navController: NavController,
  private val activity: AppCompatActivity
): NavigationService {

  override fun navigateToMovieHome() {
    val navGraph = navController.navInflater.inflate(R.navigation.home_nav_graph)
    navController.graph = navGraph
  }

  override fun navigateToMovieDetails(movieId: Int) {
    val builder = Uri.Builder()
    builder.scheme("android-app")
      .authority("com.dpfht.android.demomovieflow")
      .appendPath("movie_details_fragment")
      .appendQueryParameter("movieId", "$movieId")

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
