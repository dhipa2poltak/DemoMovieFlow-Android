package com.dpfht.android.demomovieflow.data.repository

import com.dpfht.android.demomovieflow.data.datasource.LocalDataSource
import com.dpfht.android.demomovieflow.data.datasource.RemoteDataSource
import com.dpfht.android.demomovieflow.domain.entity.MovieDomain
import com.dpfht.android.demomovieflow.domain.entity.MovieEntity
import com.dpfht.android.demomovieflow.domain.entity.Result
import com.dpfht.android.demomovieflow.domain.entity.VoidResult
import com.dpfht.android.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.android.demomovieflow.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class AppRepositoryImpl(
  private val localDataSource: LocalDataSource,
  private val remoteDataSource: RemoteDataSource
): AppRepository {

  override suspend fun getPopularMovies(page: Int): Flow<Result<MovieDomain>> {
    return remoteDataSource.getPopularMovies(page)
  }

  override suspend fun getMovieDetails(movieId: Int): Flow<Result<MovieEntity>> {
    return remoteDataSource.getMovieDetails(movieId)
  }

  override suspend fun searchMovie(query: String, page: Int): Flow<Result<MovieDomain>> {
    return remoteDataSource.searchMovie(query, page)
  }

  override suspend fun getAllFavoriteMovies(): Flow<Result<List<FavoriteMovieDBEntity>>> {
    return localDataSource.getAllFavoriteMovies()
  }

  override suspend fun getFavoriteMovie(movieId: Int): Flow<Result<FavoriteMovieDBEntity?>> {
    return localDataSource.getFavoriteMovie(movieId)
  }

  override suspend fun addFavoriteMovie(movie: MovieEntity): Flow<Result<FavoriteMovieDBEntity>> {
    return localDataSource.addFavoriteMovie(movie)
  }

  override suspend fun deleteFavoriteMovie(movie: MovieEntity): Flow<VoidResult> {
    return localDataSource.deleteFavoriteMovie(movie)
  }
}
