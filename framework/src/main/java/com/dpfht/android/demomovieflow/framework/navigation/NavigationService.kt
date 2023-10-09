package com.dpfht.android.demomovieflow.framework.navigation

interface NavigationService {

  fun navigateToMovieHome()
  fun navigateToMovieDetails(movieId: Int)
  fun navigatoErrorMessage(message: String)
}
