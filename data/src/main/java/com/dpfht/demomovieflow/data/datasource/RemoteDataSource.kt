package com.dpfht.demomovieflow.data.datasource

import com.dpfht.demomovieflow.domain.entity.MovieDomain
import com.dpfht.demomovieflow.domain.entity.MovieEntity

interface RemoteDataSource {

  suspend fun getPopularMovies(page: Int): MovieDomain
  suspend fun getMovieDetails(movieId: Int): MovieEntity
  suspend fun searchMovie(query: String, page: Int): MovieDomain
}
