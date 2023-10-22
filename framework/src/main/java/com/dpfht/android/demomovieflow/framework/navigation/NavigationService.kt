package com.dpfht.android.demomovieflow.framework.navigation

import com.dpfht.demomovieflow.domain.entity.MovieEntity

interface NavigationService {

  fun navigateToMovieHome()
  fun navigateToMovieDetails(movieId: Int, movieEntity: MovieEntity? = null, isForResult: Boolean = false)
  fun navigateToErrorMessage(message: String)
  fun navigateUp()
}
