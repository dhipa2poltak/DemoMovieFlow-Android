package com.dpfht.demomovieflow.domain.repository

import com.dpfht.demomovieflow.domain.entity.MovieDomain
import com.dpfht.demomovieflow.domain.entity.MovieEntity
import com.dpfht.demomovieflow.domain.entity.db_entity.FavoriteMovieDBEntity

interface AppRepository {

  suspend fun getPopularMovies(page: Int): MovieDomain
  suspend fun getMovieDetails(movieId: Int): MovieEntity
  suspend fun searchMovie(query: String, page: Int): MovieDomain

  suspend fun getAllFavoriteMovies(): List<FavoriteMovieDBEntity>
  suspend fun getFavoriteMovie(movieId: Int): FavoriteMovieDBEntity?
  suspend fun addFavoriteMovie(movie: MovieEntity): FavoriteMovieDBEntity
  suspend fun deleteFavoriteMovie(movie: MovieEntity)
}
