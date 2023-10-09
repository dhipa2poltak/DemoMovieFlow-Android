package com.dpfht.android.demomovieflow.data.datasource

import com.dpfht.android.demomovieflow.domain.entity.Result
import com.dpfht.android.demomovieflow.domain.entity.MovieEntity
import com.dpfht.android.demomovieflow.domain.entity.VoidResult
import com.dpfht.android.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

  suspend fun getAllFavoriteMovies(): Flow<Result<List<FavoriteMovieDBEntity>>>
  suspend fun getFavoriteMovie(movieId: Int): Flow<Result<FavoriteMovieDBEntity?>>
  suspend fun addFavoriteMovie(movie: MovieEntity): Flow<Result<FavoriteMovieDBEntity>>
  suspend fun deleteFavoriteMovie(movie: MovieEntity): Flow<VoidResult>
}
