package com.dpfht.demomovieflow.domain.repository

import com.dpfht.demomovieflow.domain.entity.Result
import com.dpfht.demomovieflow.domain.entity.MovieDomain
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.VoidResult
import com.dpfht.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import kotlinx.coroutines.flow.Flow

interface AppRepository {

  suspend fun getPopularMovies(page: Int): Flow<Result<MovieDomain>>
  suspend fun getMovieDetails(movieId: Int): Flow<Result<MovieEntity>>
  suspend fun searchMovie(query: String, page: Int): Flow<Result<MovieDomain>>

  suspend fun getAllFavoriteMovies(): Flow<Result<List<FavoriteMovieDBEntity>>>
  suspend fun getFavoriteMovie(movieId: Int): Flow<Result<FavoriteMovieDBEntity?>>
  suspend fun addFavoriteMovie(movie: MovieEntity): Flow<Result<FavoriteMovieDBEntity>>
  suspend fun deleteFavoriteMovie(movie: MovieEntity): Flow<VoidResult>
}
