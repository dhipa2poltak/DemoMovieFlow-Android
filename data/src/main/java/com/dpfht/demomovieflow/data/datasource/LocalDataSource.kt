package com.dpfht.demomovieflow.data.datasource

import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity

interface LocalDataSource {

  suspend fun getAllFavoriteMovies(): List<FavoriteMovieDBEntity>
  suspend fun getFavoriteMovie(movieId: Int): FavoriteMovieDBEntity?
  suspend fun addFavoriteMovie(movie: MovieEntity): FavoriteMovieDBEntity
  suspend fun deleteFavoriteMovie(movie: MovieEntity)
}
