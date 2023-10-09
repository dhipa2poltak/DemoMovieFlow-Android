package com.dpfht.android.demomovieflow.data.datasource

import com.dpfht.android.demomovieflow.domain.entity.MovieDomain
import com.dpfht.android.demomovieflow.domain.entity.MovieEntity
import com.dpfht.android.demomovieflow.domain.entity.Result
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

  suspend fun getPopularMovies(page: Int): Flow<Result<MovieDomain>>
  suspend fun getMovieDetails(movieId: Int): Flow<Result<MovieEntity>>
  suspend fun searchMovie(query: String, page: Int): Flow<Result<MovieDomain>>
}
