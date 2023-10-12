package com.dpfht.android.demomovieflow.framework.navigation

import com.dpfht.android.demomovieflow.domain.entity.MovieEntity

interface NavigationService {

  fun navigateToMovieHome()
  fun navigateToMovieDetails(movieId: Int, movieEntity: MovieEntity? = null, isForResult: Boolean = false)
  fun navigatoErrorMessage(message: String)

  fun navigateUp()
}
